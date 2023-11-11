package cc2;

import org.junit.jupiter.api.Test;
import stupidcoder.simulator.Simulator;

public class TestGenC4 {

    @Test
    public void genLexerBuilder() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/c4/LexerBuilder.java", false);
    }

    @Test
    public void genSyntaxAnalyzerBuilder() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/c4/SyntaxAnalyzerBuilder.java", false);
    }
}
