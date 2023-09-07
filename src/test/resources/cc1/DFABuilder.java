package com.stupidcoder.cc.lex.core;

$1$
import stupidcoder.util.ArrayUtil;

import java.util.*;

public class DFABuilder {
    $4$
    private final Map<NFANodeSet, Integer> nodeSetToState = new HashMap<>();
    private final List<NFANodeSet> stateToNodeSet = new ArrayList<>();
    private List<String> nodeToToken;
        $16$
    private int statesCount = 1;
        $$

    private final List<Boolean> accepted = new ArrayList<>();
    private final List<int[]> goTo = new ArrayList<>();
    private final List<String> tokens = new ArrayList<>();

    $$
    $50$
    private final IDfaSetter setter;

    private DFABuilder(IDfaSetter setter) {
        this.setter = setter;
    }

    $$
    $65$
    public static void build(IDfaSetter setter, NFARegexParser parser) {
        new DFABuilder(setter).build(parser.getNfa().start, parser.getNodeIdToToken());
    }

    $$
    $66$
    private void build(NFANode startNode, List<String> nfaNodeToToken) {
        $67$
        this.nodeToToken = nfaNodeToToken;
        stateToNodeSet.add(null);
        transfer(startNode);
        minimize();
        accepted.clear();
        goTo.clear();
        tokens.clear();
        $$
    }

    $$
    $20$
    private void transfer(NFANode startNode) {
        $21$
        Stack<Integer> unchecked = new Stack<>();
        createState(new NFANodeSet(epsilonClosure(Set.of(startNode))), unchecked);
        while (!unchecked.empty()) {
            $22$
            int curState = unchecked.pop();
            NFANodeSet curGroup = stateToNodeSet.get(curState);
            for (byte b = 0 ; b >= 0 ; b ++) {
                Set<NFANode> nextNodes = next(curGroup.nfaNodes, b);
                if (nextNodes.isEmpty()) {
                    continue;
                }
                NFANodeSet candidate = new NFANodeSet(nextNodes);
                int targetState = nodeSetToState.containsKey(candidate) ?
                        nodeSetToState.get(candidate) :
                        createState(candidate, unchecked);
                goTo.get(curState)[b] = targetState;
            }
            $$
        }
        stateToNodeSet.clear();
        nodeSetToState.clear();
        $$
    }

    $$
    $10$
    private int createState(NFANodeSet g, Stack<Integer> unchecked) {
        $11$
        int newId = statesCount++;
        nodeSetToState.put(g, newId);
        stateToNodeSet.add(g);
        unchecked.push(newId);
            $15$
        ensureSize(statesCount);
        for (NFANode node : g.nfaNodes) {
            if (node.accepted) {
                accepted.set(newId, true);
                tokens.set(newId, nodeToToken.get(node.id));
                return newId;
            }
        }
        return newId;
            $$
        $$
    }

    $$
    $12$
    private void ensureSize(int size) {
        $13$
        ArrayUtil.resize(accepted, size, () -> false);
        ArrayUtil.resize(goTo, size, () -> new int[128]);
        ArrayUtil.resize(tokens, size, () -> null);
        $$
    }

    $$
    $5$
    private static Set<NFANode> epsilonClosure(Set<NFANode> nodes) {
        $6$
        if (nodes.isEmpty()) {
            return nodes;
        }
        Set<NFANode> result = nodes instanceof HashSet ? nodes : new HashSet<>(nodes);
        Stack<NFANode> unchecked = new Stack<>();
        unchecked.addAll(result);
        while (!unchecked.empty()) {
            $7$
            NFANode cur = unchecked.pop();
            switch (cur.edgeType) {
                case NFANode.DOUBLE_EPSILON:
                    if (!result.contains(cur.next2)) {
                        unchecked.push(cur.next2);
                        result.add(cur.next2);
                    }
                case NFANode.SINGLE_EPSILON:
                    if (!result.contains(cur.next1)) {
                        unchecked.push(cur.next1);
                        result.add(cur.next1);
                    }
                    break;
            }
            $$
        }
        return result;
        $$
    }

    $$
    $8$
    private static Set<NFANode> next(Set<NFANode> begin, byte b) {
        $9$
        Set<NFANode> result = new HashSet<>();
        begin.stream()
                .filter(n -> n.edgeType == NFANode.CHAR && n.predicate.accept(b))
                .forEach(n -> result.add(n.next1));
        return epsilonClosure(result);
        $$
    }

    $$
    $2$
    private static class NFANodeSet {
        $3$
        protected final Set<NFANode> nfaNodes;
        private final int hash;

        public NFANodeSet(Set<NFANode> nfaNodes) {
            this.nfaNodes = nfaNodes;
            this.hash = nfaNodes.hashCode();
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof NFANodeSet && ((NFANodeSet) obj).nfaNodes.equals(nfaNodes);
        }

        @Override
        public String toString() {
            return nfaNodes.toString();
        }
        $$
    }

    $$
    $30$
    private final List<Set<Integer>> groups = new ArrayList<>();
    private final Stack<Integer> unchecked = new Stack<>();
    private int groupCount;
    private int[] stateToGroup;

    $$
    $51$
    private void minimize() {
        $52$
        stateToGroup = new int[statesCount];
        groupCount = 1;
        initGroup();
        splitGroups();
        outputData();
        stateToGroup = null;
        groups.clear();
        $$
    }

    $$
    $40$
    private void splitGroups() {
        $41$
        while (!unchecked.empty()) {
            $42$
            int curGroupId = unchecked.pop();
            Set<Integer> curGroup = groups.get(curGroupId);
            if (curGroup.size() == 1) {
                continue;
            }
            split(curGroup);
            $$
        }
        $$
    }

    $$
    $43$
    private void initGroup() {
        $44$
        groups.add(null);
        Map<String, Set<Integer>> acceptedGroups = new HashMap<>();
        Set<Integer> nonAcceptedGroup = new HashSet<>();
        for (int i = 1 ; i < statesCount ; i ++) {
            $45$
            if (accepted.get(i)) {
                String token = tokens.get(i);
                Set<Integer> group = acceptedGroups.getOrDefault(token, new HashSet<>());
                group.add(i);
                acceptedGroups.putIfAbsent(token, group);
            } else {
                nonAcceptedGroup.add(i);
            }
            $$
        }
        acceptedGroups.forEach((token, group) -> createGroup(group));
        createGroup(nonAcceptedGroup);
        $$
    }

    $$
    $33$
    private void split(Set<Integer> curGroup) {
        $34$
        Set<Integer> newGroup = null;
        int std = curGroup.iterator().next();
        curGroup.remove(std);
        List<Integer> removed = new ArrayList<>();
        for (byte b = 0 ; b >= 0 ; b ++) {
            $35$
            if (curGroup.isEmpty()) {
                break;
            }
            int stdTarget = stateToGroup[goTo.get(std)[b]];
            for (int s : curGroup) {
                $36$
                int sTarget = stateToGroup[goTo.get(s)[b]];
                if (stdTarget != sTarget) {
                    if (newGroup == null) {
                        newGroup = new HashSet<>();
                    }
                    newGroup.add(s);
                    removed.add(s);
                }
                $$
            }
            removed.forEach(curGroup::remove);
            removed.clear();
            $$
        }
        $37$
        curGroup.add(std);
        if (newGroup != null) {
            createGroup(newGroup);
        }
        $$
        $$
    }

    $$
    $31$
    private void createGroup(Set<Integer> states) {
        $32$
        int groupId = groupCount++;
        groups.add(states);
        for (int state : states) {
            stateToGroup[state] = groupId;
        }
        unchecked.add(groupId);
        $$
    }

    $$
    $53$
    private void outputData() {
        $54$
        int[] delegates = new int[groupCount];
        for (int i = 1 ; i < groups.size() ; i ++) {
            $55$
            delegates[i] = groups.get(i).iterator().next();
            $$
        }
        $$
        $56$
        for (int group = 1 ; group < groupCount ; group++) {
            $57$
            int delegate = delegates[group];
            for (byte b = 0 ; b >= 0 ; b ++) {
                $58$
                int dest = goTo.get(delegate)[b];
                if (dest > 0) {
                    setter.setGoTo(group, b, stateToGroup[dest]);
                }
                $$
            }
            $$
            $59$
            if (accepted.get(delegates[group])) {
                setter.setAccepted(group, tokens.get(delegates[group]));
            }
            $$
        }
        $$
        $60$
        setter.setStartState(stateToGroup[1]);
        setter.setDfaStatesCount(groupCount);
        $$
    }
    $$
}
$$