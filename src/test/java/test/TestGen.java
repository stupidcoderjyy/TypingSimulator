package test;

import org.junit.jupiter.api.Test;
import stupidcoder.simulator.Simulator;

public class TestGen {
    @Test
    public void test1() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/core/test1.txt", false);
    }

    @Test
    public void test2() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/core/test2.txt", false);
    }

    @Test
    public void test3() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/core/test3.txt", false);
    }

    @Test
    public void test4() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/core/test4.txt", false);
    }

    @Test
    public void test5() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/core/test5.txt", false);
    }

    @Test
    public void test6() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/core/test6.txt", false);
    }

    @Test
    public void test7() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/core/test7.txt", false);
    }

    @Test
    public void test8() {   //实现熵变配方调用
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/core/test8.txt", false);
    }
}
