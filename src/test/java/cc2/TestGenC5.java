package cc2;

import org.junit.jupiter.api.Test;
import stupidcoder.simulator.Simulator;

public class TestGenC5 {

    @Test
    public void genTokenTerminal() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/c5/TokenTerminal.java", false);
    }

    @Test
    public void genPropertySymbol() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/c5/PropertySymbol.java", false);
    }

    @Test
    public void genCompilerGenerator() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/c5/CompilerGenerator.java", false);
    }
}
