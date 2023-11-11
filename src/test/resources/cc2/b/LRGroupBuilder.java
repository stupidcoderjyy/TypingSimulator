package stupidcoder.compile.syntax;

$1$
import stupidcoder.util.Config;
import stupidcoder.util.ConsoleUtil;
import stupidcoder.util.compile.Production;
import stupidcoder.util.compile.symbol.DefaultSymbols;
import stupidcoder.util.compile.symbol.Symbol;

import java.util.*;

public class LRGroupBuilder {
$2$
    $3$
    public static final int SYNTAX_DEBUG_INFO = Config.register(Config.BOOL_T, false);
    public static final int SYNTAX_CONFLICT_INFO = Config.register(Config.BOOL_T, false);
    private final SyntaxLoader loader;
    private final ISyntaxAnalyzerSetter receiver;
    private final List<LRGroup> idToCore = new ArrayList<>();
    private final Map<LRGroup, Integer> coreToId = new HashMap<>();
    private final Map<LRItem, List<LRItem>> spreadMap = new HashMap<>();
    private final List<Map<Symbol, LRGroup>> groupToTargets = new ArrayList<>();
    private boolean printDebug, showConflict;
    $$
    $70$

    public static void build(SyntaxLoader l, ISyntaxAnalyzerSetter receiver) {
        new LRGroupBuilder(l, receiver).build();
    }

    private LRGroupBuilder(SyntaxLoader l, ISyntaxAnalyzerSetter receiver) {
        this.loader = l;
        this.receiver = receiver;
    }

    public void build() {
        $71$
        printDebug = Config.getBool(SYNTAX_DEBUG_INFO);
        showConflict = Config.getBool(SYNTAX_CONFLICT_INFO);
        LRItem root = new LRItem(loader.root(), 0);
        registerCore(new LRGroup(List.of(root), 0));
        expandGroups();
        spreadSymbols(root);
        emitActions();
        $$
    }
    $$
    $4$

    private final Stack<LRGroup> unchecked = new Stack<>();
    private Map<Symbol, Set<LRItem>> tempGoto;
    private List<Integer> spreadSrc;
    private List<LRItem> tempCoreItems;
    private LRGroup tempGroup;
    private LRGroup curCore;

    private void expandGroups() {
        $25$
        while (!unchecked.empty()) {
            $26$
            tempGoto = new HashMap<>();
            spreadSrc = new ArrayList<>();
            tempCoreItems = new ArrayList<>();
            tempGroup = new LRGroup();
            curCore = unchecked.pop();
            initTempItems(curCore);
            expandTempItems();
            tempGoto.forEach(this::buildTargetCores);
            $$
        }
        $$
    }

    private void initTempItems(LRGroup core) {
        $5$
        for (LRItem item : core.items) {
            $6$
            int id = tempGroup.items.size();
            LRItem temp = new LRItem(item.production, item.point, id);
            tempGroup.insertItem(temp);
            unexpanded.push(temp);
            spreadSrc.add(id);
            tempCoreItems.add(item);
            $$
        }
        $$
    }

    private void expandTempItems() {
        $15$
        while (!unexpanded.isEmpty()) {
            $16$
            LRItem item = unexpanded.pop();
            Symbol next = item.nextSymbol();
            if (next == null || next == DefaultSymbols.EPSILON) {
                continue;
            }
            setDest(next, item);
            if (next.isTerminal) {
                continue;
            }
            boolean spread = loader.calcForward(tempForward, item.production, item.point);
            if (spread) {
                tempForward.addAll(item.forwardSymbols);
            }
            for (Production p : loader.productionsWithHead(next)) {
                $17$
                LRItem other = tempGroup.getItem(p, 0);
                boolean create = other == null;
                if (create) {
                    $18$
                    other = new LRItem(p, 0, tempGroup.items.size());
                    tempGroup.insertItem(other);
                    unexpanded.push(other);
                    spreadSrc.add(-1);
                    $$
                }
                $$
                $19$
                if (spread) {
                    $20$
                    int preSrc = spreadSrc.get(item.id);
                    if (preSrc >= 0) {
                        spreadSrc.set(other.id, preSrc);
                    }
                    $$
                }
                $$
                $21$
                int pre = other.forwardSymbols.size();
                other.forwardSymbols.addAll(tempForward);
                if (!create && other.forwardSymbols.size() > pre) {
                    unexpanded.add(other); //
                }
                $$
            }
            tempForward.clear();
            $$
        }
        $$
    }

    private void setDest(Symbol input, LRItem item) {
        $7$
        if (tempGoto.containsKey(input)) {
            tempGoto.get(input).add(item);
        } else {
            Set<LRItem> items = new HashSet<>();
            items.add(item);
            tempGoto.put(input, items);
        }
        $$
    }

    private void buildTargetCores(Symbol input, Set<LRItem> items) {
        $12$
        LRGroup target = getOrCreateCore(items);
        groupToTargets.get(curCore.id).put(input, target);
        for (LRItem temp : items) {
            LRItem targetItem = target.getItem(temp.production, temp.point + 1);
            if (spread(temp, targetItem)) {
                itemsToSpread.push(targetItem);
            }
            if (spreadSrc.get(temp.id) < 0) {
                continue;
            }
            LRItem src = tempCoreItems.get(spreadSrc.get(temp.id));
            setSpread(src, targetItem);
        }
        $$
    }

    private boolean spread(LRItem src, LRItem dest) {
        $8$
        int pre = dest.forwardSymbols.size();
        dest.forwardSymbols.addAll(src.forwardSymbols);
        return dest.forwardSymbols.size() > pre;
        $$
    }

    private void setSpread(LRItem from, LRItem to) {
        $9$
        if (spreadMap.containsKey(from)) {
            spreadMap.get(from).add(to);
        } else {
            List<LRItem> targets = new ArrayList<>();
            targets.add(to);
            spreadMap.put(from, targets);
        }
        $$
    }
    
    private LRGroup getOrCreateCore(Set<LRItem> items) {
        $11$
        LRGroup target = new LRGroup(idToCore.size());
        for (LRItem item : items) {
            LRItem next = new LRItem(item.production, item.point + 1);
            target.insertItem(next);
        }
        if (coreToId.containsKey(target)) {
            target = idToCore.get(coreToId.get(target));
        } else {
            for (LRItem item : target.items) {
                item.id = target.id;
            }
            registerCore(target);
        }
        return target;
        $$
    }

    private void registerCore(LRGroup g) {
        $10$
        idToCore.add(g);
        coreToId.put(g, g.id);
        unchecked.push(g);
        groupToTargets.add(new HashMap<>());
        $$
    }
    $$ //4
    $30$

    private final Stack<LRItem> itemsToSpread = new Stack<>();

    private void spreadSymbols(LRItem root) {
        $31$
        if (loader.endTerminals.isEmpty()) {
            loader.endTerminals.add(DefaultSymbols.EOF); //默认结束符号
        }
        root.forwardSymbols.addAll(loader.endTerminals);
        itemsToSpread.push(root);
        while (!itemsToSpread.empty()) {
            $32$
            LRItem src = itemsToSpread.pop();
            if (!spreadMap.containsKey(src)) {
                continue;
            }
            for (LRItem dest : spreadMap.get(src)) {
                $33$
                if (!spread(src, dest) || !spreadMap.containsKey(dest)) {
                    continue;
                }
                itemsToSpread.push(dest);
                $$
            }
            $$
        }
        $$
    }

    $$
    $35$
    private Map<Symbol, Production> tempReduceMap;

    private void emitActions() {
        $65$
        if (receiver == null) {
            return;
        }
        $$
        $66$
        for (LRGroup group : idToCore) {
            $67$
            tempReduceMap = new HashMap<>();
            expand(group);
            if (printDebug) {
                printGroupInfo(group);
            }
            emitActions(group);
            group.items.clear();
            group.hashToItem.clear();
            $$
        }
        $$
        $68$
        receiver.setStatesCount(idToCore.size());
        receiver.setOthers(loader);
        $$
    }

    private final Deque<LRItem> unexpanded = new ArrayDeque<>();
    private final Set<Symbol> tempForward = new HashSet<>();

    private void expand(LRGroup core) {
        $36$
        unexpanded.addAll(core.items);
        while (!unexpanded.isEmpty()) {
            $37$
            LRItem item = unexpanded.pop();
            Symbol next = item.nextSymbol();
            if (next == null || next.isTerminal) {
                continue;
            }
            if (loader.calcForward(tempForward, item.production, item.point)) {
                tempForward.addAll(item.forwardSymbols);
            }
            for (Production p : loader.productionsWithHead(next)) {
                $38$
                LRItem other = core.getItem(p, 0);
                if (other == null) {
                    other = core.registerItem(p);
                    unexpanded.push(other);
                }
                other.forwardSymbols.addAll(tempForward);
                $$
            }
            tempForward.clear();
            $$
        }
        $$
    }

    private void emitActions(LRGroup core) {
        $60$
        Map<Symbol, LRGroup> goToMap = groupToTargets.get(core.id);
        Set<Integer> addedTarget = new HashSet<>();
        for (LRItem item : core.items) {
            $61$
            Symbol next = item.nextSymbol();
            if (next == null || next == DefaultSymbols.EPSILON) {
                emitReduceAndAccept(item, goToMap, core);
            } else {
                emitGotoAndShift(next, goToMap, core, addedTarget);
            }
            $$
        }
        $$
    }

    private void emitGotoAndShift(Symbol next, Map<Symbol, LRGroup> goToMap, LRGroup core, Set<Integer> addedTarget) {
        $55$
        int targetCore = goToMap.get(next).id;
        if (next.isTerminal) {
            $56$
            receiver.setActionShift(core.id, targetCore, next.id);
            if (printDebug) {
                printShift(next, targetCore);
            }
            $$
        } else {
            $57$
            if (addedTarget.contains(targetCore)) {
                return;
            }
            addedTarget.add(targetCore);
            receiver.setGoto(core.id, targetCore, next.id);
            if (printDebug) {
                printGoto(next, targetCore);
            }
            $$
        }
        $$
    }

    private void emitReduceAndAccept(LRItem item, Map<Symbol, LRGroup> goToMap, LRGroup core) {
        $40$
        if (item.production == loader.root()) {
            $41$
            for (Symbol end : loader.endTerminals) {
                $42$
                receiver.setActionAccept(core.id, end.id);
                if (printDebug) {
                    printAccept(end);
                }
                $$
            }
            return;
            $$
        }
        $$
        $43$
        for (Symbol f : item.forwardSymbols) {
            $44$
            boolean conflictSR = goToMap.containsKey(f);
            if (showConflict) {
                $45$
                boolean conflictRR = tempReduceMap.containsKey(f);
                if (conflictRR) {
                    $46$
                    if (tempReduceMap.get(f).id() != item.production.id()) {
                        warnConflictRR(tempReduceMap.get(f), item.production, f);
                    }
                    $$
                } else {
                    tempReduceMap.put(f, item.production);
                }
                $$
            }
            $$
            $47$
            if (!conflictSR || loader.shouldReduce(item.production, f)) {
                $48$
                receiver.setActionReduce(core.id, f.id, item.production.id());
                if (printDebug) {
                    printReduce(f, item.production);
                }
                if (conflictSR && showConflict) {
                    warnConflictSR(item, f, false);
                }
                continue;
                $$
            }
            $$
            $49$
            if (showConflict) {
                warnConflictSR(item, f, true);
            }
            $$
        }
        $$
    }

    private void warnConflictSR(LRItem item, Symbol f, boolean shift) {
        ConsoleUtil.begin(0, ConsoleUtil.RED);
        System.out.print("shift-reduce conflict:");
        System.out.print("    prod:" + item.production);
        System.out.print("    forward:'" + f + "'");
        System.out.print("    action:" + (shift ? "SHIFT" : "REDUCE"));
        ConsoleUtil.end();
        System.out.println();
    }

    private void warnConflictRR(Production pre, Production cur, Symbol f) {
        ConsoleUtil.begin(0, ConsoleUtil.BLACK, ConsoleUtil.BG_RED);
        System.err.print("reduce-reduce conflict:");
        System.err.print("    prod1:" + pre);
        System.err.print("    prod2:" + cur);
        System.err.print("    forward: " + f);
        ConsoleUtil.end();
        System.out.println();
    }

    private void printGroupInfo(LRGroup group) {
        System.out.println("\r\ngroup " + group.id + ":");
        for (LRItem item : group.items) {
            ConsoleUtil.printPurple(item.toString());
            System.out.println();
        }
    }

    private void printReduce(Symbol forward, Production p) {
        ConsoleUtil.printHighlightBlue("REDUCE");
        System.out.print("        forward:");
        String fs = forward.toString();
        ConsoleUtil.printGreen(fs);
        System.out.print(" ".repeat(22 - fs.length()) + "prod:");
        ConsoleUtil.printPurple(p.toString());
        System.out.println();
    }

    private void printGoto(Symbol in, int next) {
        ConsoleUtil.printHighlightWhite("GOTO");
        System.out.print("          input:");
        String is = in.toString();
        ConsoleUtil.printGreen(is);
        System.out.print(" ".repeat(24 - is.length()) + "to:");
        ConsoleUtil.printPurple(String.valueOf(next));
        System.out.println();
    }

    private void printShift(Symbol input, int next) {
        ConsoleUtil.printHighlightYellow("SHIFT");
        System.out.print("         input:");
        String is = input.toString();
        ConsoleUtil.printGreen(is);
        System.out.print(" ".repeat(24 - is.length()) + "next:");
        ConsoleUtil.printPurple(String.valueOf(next));
        System.out.println();
    }

    private void printAccept(Symbol s) {
        ConsoleUtil.printHighlightPurple("ACCEPT");
        System.out.print("        forward:");
        ConsoleUtil.printGreen(s);
        System.out.println();
    }
    $$ //35
$$
}
$$