package dfa;

import org.junit.jupiter.api.Test;
import stupidcoder.simulator.Simulator;

public class TestGenRegexAndNfa {
    @Test
    public void genNFANode1() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(1, 6);
        s.run("/cc1/NFANode.java", false);
    }

    @Test
    public void genICP() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc1/ICharPredicate.java", false);
    }

    @Test
    public void genNFANode2() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(7, 100);
        s.run("/cc1/NFANode.java", false);
    }

    @Test
    public void genNFA() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc1/NFA.java", false);
    }

    //粘贴工具类

    @Test
    public void genNFARegexParser() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc1/NFARegexParser.java", false);
    }
}
