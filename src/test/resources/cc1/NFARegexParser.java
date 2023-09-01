package stupidcoder..lex;

$1$
import stupidcoder.util.ASCII;
import stupidcoder.util.ArrayUtil;
import stupidcoder.util.input.IInput;
import stupidcoder.util.input.StringInput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFARegexParser {
    $3$
    private IInput input;
    $$
    $50$

    private NFA nfa;
    private final List<String> nodeIdToToken = new ArrayList<>();

    public void register(String regex, String token) {
        input = new StringInput(regex);
        NFA regexNfa = expr();
        $52$
        setAcceptedNode(regexNfa, token);
        if (nfa == null) {
            nfa = regexNfa;
        } else {
            NFANode newStart = new NFANode();
            newStart.addEpsilonEdge(regexNfa.start, nfa.start);
            nfa.start = newStart;
        }
        $$
    }

    private void setAcceptedNode(NFA target, String token) {
        $51$
        target.end.accepted = true;
        ArrayUtil.resize(nodeIdToToken, target.end.id + 1);
        nodeIdToToken.set(target.end.id, token);
        $$
    }
    $$
    $55$

    public NFA getNfa() {
        return nfa;
    }

    public List<String> getNodeIdToToken() {
        return nodeIdToToken;
    }
    $$
    $40$

    private NFA expr() {
        $45$
        NFA result = new NFA();
        while (input.available()) {
            $46$
            int b = input.read();
            switch (b) {
                $47$
                case ')' -> {
                    return result;
                }
                case '|' -> {
                    input.read();
                    result.or(seq());
                }
                default -> result.and(seq());
                $$
            }
            $$
        }
        return result;
        $$
    }
    $$
    $41$

    private NFA seq() {
        $42$
        input.retract();
        NFA result = new NFA();
        while (input.available()) {
            $43$
            int b = input.read();
            switch (b) {
                $44$
                case '(' -> result.and(checkClosure(expr()));
                case '|', ')' -> {
                    input.retract();
                    return result;
                }
                default -> result.and(atom());
                $$
            }
            $$
        }
        return result;
        $$
    }
    $$
    $4$

    private NFA atom() {
        $5$
        input.retract();
        int b = input.read();
        ICharPredicate predicate;
        switch (b) {
            $20$
            case '[' -> predicate = clazz();
            case '\\' -> predicate = ICharPredicate.single(input.read());
            $$
            $30$
            case '@' -> predicate = escape(input.read());
            default -> predicate = ICharPredicate.single(b);
            $$
        }
        $$
        $35$
        return checkClosure(new NFA().andAtom(predicate));
        $$
    }
    $$
    $31$

    private NFA checkClosure(NFA target) {
        $32$
        if (input.available()) {
            $33$
            switch (input.read()) {
                $34$
                case '*' -> target.star();
                case '+' -> target.plus();
                case '?' -> target.quest();
                default -> input.retract();
                $$
            }
            $$
        }
        return target;
        $$
    }
    $$
    $15$

    private ICharPredicate clazz() {
        $16$
        ICharPredicate result = null;
        while (input.available()) {
            $17$
            int b = input.read();
            switch (b) {
                $18$
                case '[' -> result = ICharPredicate.or(result, clazz());
                case ']' -> {
                    return result;
                }
                default -> result = ICharPredicate.or(result, minClazzPredicate());
                $$
            }
            $$
        }
        return result;
        $$
    }
    $$
    $6$

    private ICharPredicate minClazzPredicate() {
        $7$
        input.retract();
        int b = input.read();
        if (b == '^') {
            $8$
            Set<Integer> excluded = new HashSet<>();
            while (input.available()) {
                $9$
                int b1 = input.read();
                if (b1 == '[' || b1 == ']') {
                    input.retract();
                    break;
                }
                excluded.add(b1);
                $$
            }
            return c -> !excluded.contains(c);
            $$
        }
        $$
        $10$
        int b1 = input.read();
        if (b1 == '-') {
            $11$
            int b2 = input.read();
            if (b2 == '[' || b2 == ']') {
                $12$
                input.retract();
                return c -> c == b || c == '-';
                $$
            }
            $$
            $13$
            return ICharPredicate.ranged(b, b2);
            $$
        }
        $$
        $14$
        input.retract();
        return ICharPredicate.single(b);
        $$
    }
    $$
    $25$

    private ICharPredicate escape(int b) {
        $26$
        return switch (b) {
            $27$
            case 'D', 'd' -> ASCII::isDigit;
            case 'W', 'w' -> ASCII::isWord;
            case 'U', 'u' -> ASCII::isAlnum;
            case 'H', 'h' -> ASCII::isHex;
            case 'A', 'a' -> ASCII::isAlpha;
            default -> ch -> true;
            $$
        };
        $$
    }
    $$
}
$$