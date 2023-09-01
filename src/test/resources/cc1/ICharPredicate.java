package com.stupidcoder.cc.lex;

$1$
@FunctionalInterface
public interface ICharPredicate {
    $2$
    boolean accept(int input);

    static ICharPredicate single(int ch) {
        $3$
        return c -> c == ch;
        $$
    }

    $$
    $4$
    static ICharPredicate and(ICharPredicate c1, ICharPredicate c2) {
        $5$
        return c -> c1.accept(c) && c2.accept(c);
        $$
    }

    $$
    $6$
    static ICharPredicate or(ICharPredicate c1, ICharPredicate c2) {
        $7$
        if (c1 == null) {
            return c2;
        }
        if (c2 == null) {
            return c1;
        }
        return c -> c1.accept(c) || c2.accept(c);
        $$
    }

    $$
    $8$
    static ICharPredicate ranged(int start, int end) {
        $9$
        return c -> c >= start && c <= end;
        $$
    }
    $$
}
$$