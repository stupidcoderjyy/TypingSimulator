package cc2;

import org.junit.jupiter.api.Test;
import stupidcoder.simulator.Simulator;

public class TestGenB {

    @Test
    public void genLRItem() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/b/LRItem.java", false);
    }

    @Test
    public void genLRGroup() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/b/LRGroup.java", false);
    }

    @Test
    public void genSyntaxLoader() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/b/SyntaxLoader.java", false);
    }

    @Test
    public void genISyntaxAnalyzerSetter() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/b/ISyntaxAnalyzerSetter.java", false);
    }

    @Test
    public void genLRGroupBuilder() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/b/LRGroupBuilder.java", false);
    }
}
