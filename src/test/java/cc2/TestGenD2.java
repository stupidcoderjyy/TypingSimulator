package cc2;

import org.junit.jupiter.api.Test;
import stupidcoder.simulator.Simulator;

public class TestGenD2 {

    @Test
    public void genCodes() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/d2/Codes.java", false);
    }

    @Test
    public void genFunction() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/d2/Function.java", false);
    }

    @Test
    public void genFuncDefined() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/d2/FuncDefined.java", false);
    }

    @Test
    public void genFuncPrintln() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/d2/FuncPrintln.java", false);
    }

    @Test
    public void genFuncRand() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/d2/FuncRand.java", false);
    }

    @Test
    public void genStackFrame() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/d2/StackFrame.java", false);
    }

    @Test
    public void genEnv() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/d2/Env.java", false);
    }
}
