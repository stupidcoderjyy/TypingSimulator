package com.stupidcoder.cc.lex;

$1$
import java.util.Stack;

public class NFA {
    $2$
    protected NFANode start, end;

    private boolean isEmpty() {
        $3$
        return start == null || end == null;
        $$
    }

    $$
    $4$
    public void and(NFA other) {
        $5$
        if (isEmpty()) {
            $6$
            start = other.start;
            end = other.end;
            return;
            $$
        }
        $$
        $7$
        end.addEpsilonEdge(other.start);
        end = other.end;
        $$
    }

    $$
    $8$
    public NFA andAtom(ICharPredicate predicate) {
        $9$
        NFANode newStart = new NFANode();
        NFANode newEnd = new NFANode();
        newStart.addCharEdge(predicate, newEnd);
        if (isEmpty()) {
            $10$
            start = newStart;
            end = newEnd;
            return this;
            $$
        }
        $$
        $11$
        end.addEpsilonEdge(newStart);
        end = newEnd;
        return this;
        $$
    }
    $$
    $12$

    public void star() {
        $13$
        NFANode newStart = new NFANode();
        NFANode newEnd = new NFANode();
        newStart.addEpsilonEdge(start, newEnd);
        end.addEpsilonEdge(start, newEnd);
        start = newStart;
        end = newEnd;
        $$
    }
    $$
    $14$

    public void quest() {
        $15$
        NFANode newStart = new NFANode();
        NFANode newEnd = new NFANode();
        newStart.addEpsilonEdge(start, newEnd);
        end.addEpsilonEdge(newEnd);
        start = newStart;
        end = newEnd;
        $$
    }
    $$
    $16$

    public void plus() {
        $17$
        NFANode newStart = new NFANode();
        NFANode newEnd = new NFANode();
        newStart.addEpsilonEdge(start);
        end.addEpsilonEdge(start, newEnd);
        start = newStart;
        end = newEnd;
        $$
    }
    $$
    $18$

    public void or(NFA other) {
        $19$
        if (isEmpty()) {
            $20$
            start = other.start;
            end = other.end;
            return;
            $$
        }
        $$
        $21$
        NFANode newStart = new NFANode();
        NFANode newEnd = new NFANode();
        newStart.addEpsilonEdge(start, other.start);
        end.addEpsilonEdge(newEnd);
        other.end.addEpsilonEdge(newEnd);
        start = newStart;
        end = newEnd;
        $$
    }
    $$
    $22$

    @Override
    public String toString() {
        $23$
        if (isEmpty()) {
            return "null";
        } else {
            return start + ", " + end;
        }
        $$
    }
    $$
    $24$

    public void print() {
        $25$
        if (isEmpty()) {
            return;
        }
        boolean[] printed = new boolean[NFANode.nodeCount];
        Stack<NFANode> unchecked = new Stack<>();
        unchecked.push(start);
        while (!unchecked.empty()) {
            $26$
            NFANode node = unchecked.pop();
            System.out.println(node);
            switch (node.edgeType) {
                $27$
                case NFANode.DOUBLE_EPSILON:
                    if (!printed[node.next2.id]) {
                        unchecked.push(node.next2);
                        printed[node.next2.id] = true;
                    }
                case NFANode.SINGLE_EPSILON:
                case NFANode.CHAR:
                    if (!printed[node.next1.id]) {
                        unchecked.push(node.next1);
                        printed[node.next1.id] = true;
                    }
                    break;
                $$
            }
            $$
        }
        $$
    }
    $$
}
$$
