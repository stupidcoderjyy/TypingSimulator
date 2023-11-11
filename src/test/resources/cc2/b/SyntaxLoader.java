package stupidcoder.compile.syntax;

$1$
import stupidcoder.util.ArrayUtil;
import stupidcoder.util.compile.Production;
import stupidcoder.util.compile.symbol.DefaultSymbols;
import stupidcoder.util.compile.symbol.Symbol;

import java.util.*;

public class SyntaxLoader {
$2$
    $3$
    public final List<Production> productions = new ArrayList<>();
    public final Map<Integer, Integer> terminalIdRemap = new HashMap<>();
    public final Map<String, Symbol> lexemeToSymbol = new HashMap<>();
    public final Set<Symbol> endTerminals = new HashSet<>();
    public int productionCount = 0;
    public int terminalCount = 1;
    public int nonTerminalCount = 1;
    private final List<List<Production>> symbolToProductions = new ArrayList<>();
    private final List<Integer> terminalPriority = new ArrayList<>();
    private final List<Integer> productionPriority = new ArrayList<>();
    private Production extendedRoot;
    private Symbol tempStart;
    private List<Symbol> tempProduction = new ArrayList<>();
    private boolean setEndSymbols = false;

    public SyntaxLoader() {
        symbolToProductions.add(new ArrayList<>());
    }

    $$
    $20$
    public void beginProd(String head) {
        $21$
        setEndSymbols = false;
        tempStart = registerNonTerminal(head);
        if (extendedRoot == null) {
            $22$
            Symbol root = DefaultSymbols.ROOT;
            lexemeToSymbol.put(root.toString(), root);
            addProduction(root, List.of(tempStart), false);
            extendedRoot = productions.get(0);
            $$
        }
        $$
    }

    $$
    $24$
    public void beginEndSymbolsCustomize() {
        setEndSymbols = true;
    }

    $$
    $11$
    public void add(Symbol s) {
        $12$
        if (setEndSymbols) {
            $13$
            if (!s.isTerminal) {
                throw new RuntimeException("end symbol should be terminal");
            }
            if (s == DefaultSymbols.EPSILON) {
                throw new RuntimeException("end symbol shouldn't be epsilon");
            }
            endTerminals.add(s);
            return;
            $$
        }
        $$
        $14$
        if (s == DefaultSymbols.EPSILON) {
            tempProduction.add(s);
            lexemeToSymbol.put("ε", s);
            return;
        }
        if (s.isTerminal) {
            addTerminal(s.toString(), s.id);
        } else {
            addNonTerminal(s.toString());
        }
        $$
    }

    $$
    $9$
    public void addNonTerminal(String lexeme) {
        $10$
        if (setEndSymbols) {
            throw new RuntimeException("end symbol should be terminal");
        }
        tempProduction.add(registerNonTerminal(lexeme));
        $$
    }
    $$

    $6$
    public void addTerminal(String lexeme, int id) {
        $7$
        if (lexemeToSymbol.containsKey(lexeme)) {
            Symbol s = lexemeToSymbol.get(lexeme);
            if (setEndSymbols) {
                endTerminals.add(s);
            } else {
                tempProduction.add(s);
            }
        } else {
            Symbol s = new Symbol(lexeme, true, terminalCount++);
            if (setEndSymbols) {
                endTerminals.add(s);
            } else {
                tempProduction.add(s);
                lexemeToSymbol.put(lexeme, s);
            }
            terminalIdRemap.put(id, s.id);
        }
        $$
    }

    $$
    $8$
    public void addTerminal(char ch) {
        addTerminal(String.valueOf(ch), ch);
    }

    $$
    $4$
    private Symbol registerNonTerminal(String lexeme) {
        $5$
        if (lexemeToSymbol.containsKey(lexeme)) {
            return lexemeToSymbol.get(lexeme);
        }
        Symbol s = new Symbol(lexeme, false, nonTerminalCount++);
        lexemeToSymbol.put(lexeme, s);
        return s;
        $$
    }

    $$
    $30$
    public void setPriority(int value) {
        $31$
        if (setEndSymbols) {
            throw new RuntimeException("cannot set priority to end symbols");
        }
        if (tempProduction.isEmpty()) {
            throw new RuntimeException("empty production");
        }
        Symbol pre = tempProduction.get(tempProduction.size() - 1);
        if (!pre.isTerminal) {
            throw new RuntimeException("cannot set priority to a nonTerminal symbol");
        }
        ArrayUtil.resize(terminalPriority, pre.id + 1,  () -> 0);
        terminalPriority.set(pre.id, value);
        $$
    }

    $$
    $32$
    public void finish() {
        finish0(false);
    }

    public void finish(int priority) {
        finish0(true);
        ArrayUtil.resize(productionPriority, productionCount, () -> 0);
        productionPriority.set(productionCount - 1, priority);
    }

    $$
    $33$
    private void finish0(boolean customPriority) {
        $34$
        if (setEndSymbols) {
            $35$
            if (customPriority) {
                throw new RuntimeException("cannot set priority to end symbols production");
            }
            return;
            $$
        }
        $$
        $36$
        addProduction(tempStart, tempProduction, customPriority);
        tempProduction = new ArrayList<>();
        $$
    }

    $$
    $40$
    private void addProduction(Symbol head, List<Symbol> body, boolean customPriority) {
        $41$
        ArrayUtil.resize(symbolToProductions, head.id + 1, ArrayList::new);
        Production p = new Production(productionCount, head, body);
        symbolToProductions.get(head.id).add(p);
        productions.add(p);
        productionCount++;
        if (customPriority) {
            return;
        }
        int size = body.size();
        $$
        $42$
        for (int i = size - 1 ; i >= 0 ; i --) {
            $43$
            Symbol s = body.get(i);
            if (s.isTerminal && s != DefaultSymbols.EPSILON) {
                $44$
                ArrayUtil.resize(productionPriority, p.id() + 1, () -> 0);
                ArrayUtil.resize(terminalPriority, s.id + 1, () -> 0);
                productionPriority.set(p.id(), terminalPriority.get(s.id));
                break;
                $$
            }
            $$
        }
        $$
    }

    $$
    $45$
    public List<Production> productionsWithHead(Symbol head) {
        $46$
        if (head.isTerminal) {
            throw new RuntimeException("terminal");
        }
        if (head.id >= symbolToProductions.size() || symbolToProductions.get(head.id).isEmpty()) {
            throw new RuntimeException("no productions found, head:" + head);
        }
        return symbolToProductions.get(head.id);
        $$
    }

    $$
    $50$
    public Production root() {
        return extendedRoot;
    }

    public boolean shouldReduce(Production target, Symbol forward) {
        return productionPriority.get(target.id()) >= terminalPriority.get(forward.id);
    }

    private static final Set<Integer> tempChecked = new HashSet<>();

    boolean calcForward(Set<Symbol> result, Production g, int point) {
        $51$
        List<Symbol> symbolsBeta = new ArrayList<>(
                g.body().subList(point + 1, g.body().size()));
        if (symbolsBeta.isEmpty()) {
            return true;
        } else {
            first(result, symbolsBeta);
        }
        tempChecked.clear();
        return result.remove(DefaultSymbols.EPSILON);
        $$
    }

    $$
    $60$
    private void first(Set<Symbol> result, List<Symbol> symbols) {
        $61$
        for (Symbol s : symbols) {
            first(result, s);
            if (!result.contains(DefaultSymbols.EPSILON)) {
                break;
            }
        }
        $$
    }
    $$
    $62$

    private void first(Set<Symbol> result, Symbol s) {
        $63$
        if (s.isTerminal) {
            $64$
            result.add(s);
            return;
            $$
        }
        $$
        $65$
        for (Production g : productionsWithHead(s)) {
            $66$
            if (tempChecked.contains(g.id())) {
                continue;
            } else {
                tempChecked.add(g.id());
            }
            for (Symbol symbol : g.body()) {
                $67$
                first(result, symbol);
                if (!result.contains(DefaultSymbols.EPSILON)) {
                    break;
                }
                $$
            }
            $$
        }
        $$
    }
    $$
$$
}
$$