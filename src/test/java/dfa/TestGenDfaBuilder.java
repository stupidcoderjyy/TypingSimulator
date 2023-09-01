package dfa;

import org.junit.jupiter.api.Test;
import stupidcoder.simulator.Simulator;

public class TestGenDfaBuilder {

    @Test
    public void genNfaToDfa() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(0, 49);
        s.run("/cc1/DFABuilder.java", false);
    }

    @Test
    public void genIDfaSetter() {
        Simulator s = new Simulator();
        s.setWaitTime(3000);
        s.run("/cc1/IDfaSetter.java", false);
    }

    @Test
    public void genDfaMinimize() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(50, 100);
        s.run("/cc1/DFABuilder.java", false);
    }
}
