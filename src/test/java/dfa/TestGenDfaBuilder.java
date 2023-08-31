package dfa;

import org.junit.jupiter.api.Test;
import stupidcoder.simulator.Simulator;

public class TestGenDfaBuilder {

    @Test
    public void genNfaToDfa() {
        Simulator.run("/DFABuilder.java", 5000);
    }

    @Test
    public void genDfaMinimize() {
        Simulator.run("/DFABuilder.java", 3000);
    }
}
