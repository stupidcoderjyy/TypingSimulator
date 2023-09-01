package com.stupidcoder.cc.lex;

$1$
public class NFANode {
    $5$
    protected static final byte NO_EDGE = 0;
    protected static final byte SINGLE_EPSILON = 1;
    protected static final byte DOUBLE_EPSILON = 2;
    protected static final byte CHAR = 3;
    $$
    $15$
    protected static int nodeCount = 0;
    $$
    $6$

    protected byte edgeType = NO_EDGE;
    protected NFANode next1, next2;
    $$
    $7$
    protected ICharPredicate predicate;
    $$
    $16$
    protected boolean accepted = false;
    protected final int id;

    protected NFANode() {
        id = nodeCount++;
    }
    $$
    $8$

    public void addEpsilonEdge(NFANode next) {
        $9$
        if (edgeType == NO_EDGE) {
            next1 = next;
            edgeType = SINGLE_EPSILON;
        } else if (edgeType == SINGLE_EPSILON) {
            next2 = next;
            edgeType = DOUBLE_EPSILON;
        }
        $$
    }
    $$
    $10$

    public void addEpsilonEdge(NFANode next1, NFANode next2) {
        $11$
        this.next1 = next1;
        this.next2 = next2;
        edgeType = DOUBLE_EPSILON;
        $$
    }
    $$
    $12$

    public void addCharEdge(ICharPredicate predicate, NFANode next) {
        $13$
        this.predicate = predicate;
        next1 = next;
        edgeType = CHAR;
        $$
    }
    $$
    $17$

    @Override
    public String toString() {
        $18$
        String str = id + "(";
        switch (edgeType) {
            $19$
            case NO_EDGE -> str += "null";
            case SINGLE_EPSILON -> str += "ε" + next1.id;
            case DOUBLE_EPSILON -> str += "ε" + next1.id + ",ε" + next2.id;
            case CHAR -> str += "c" + next1.id;
            $$
        }
        $$
        $20$
        str += ')';
        return str;
        $$
    }
    $$
    $21$

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NFANode && obj.hashCode() == id;
    }
    $$
}
$$
