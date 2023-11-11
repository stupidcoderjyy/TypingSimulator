package cc2;

import org.junit.jupiter.api.Test;
import stupidcoder.simulator.Simulator;

public class TestGenD3 {

    @Test
    public void genField() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/d3/Field.java", false);
    }

    @Test
    public void genEnvBuilder() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.run("/cc2/d3/EnvBuilder.java", false);
    }

    @Test
    public void genTokenInt() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/d3/TokenInt.java", false);
    }

    @Test
    public void genPropertyExpr1() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 24);
        s.run("/cc2/d3/PropertyExpr.java", false);
    }

    @Test
    public void genPropertyFieldName() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyFieldName.java", false);
    }

    @Test
    public void genPropertyVar1() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 4);
        s.run("/cc2/d3/PropertyVar.java", false);
    }

    @Test
    public void genPropertyVarArrBegin() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyVarArrBegin.java", false);
    }

    @Test
    public void genPropertyVar2() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(5, 100);
        s.run("/cc2/d3/PropertyVar.java", false);
    }

    @Test
    public void genPropertyBaseType() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyBaseType.java", false);
    }

    @Test
    public void genPropertyType() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyType.java", false);
    }

    @Test
    public void genPropertyFieldDef() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyFieldDef.java", false);
    }

    @Test
    public void genPropertyArrDef() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyArrDef.java", false);
    }

    @Test
    public void genPropertyExpr2() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(25, 100);
        s.run("/cc2/d3/PropertyExpr.java", false);
    }

    @Test
    public void genPropertyStmt() {
        Simulator s = new Simulator();
        s.setWaitTime(2000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyStmt.java", false);
    }

    @Test
    public void genWhile() {
        Simulator s = new Simulator();
        s.setWaitTime(4000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyWhileCondBegin.java", false);
        s.run("/cc2/d3/PropertyWhileCondEnd.java", false);
        s.run("/cc2/d3/PropertyWhileEnd.java", false);
    }

    @Test
    public void genIf() {
        Simulator s = new Simulator();
        s.setWaitTime(4000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyIfCondEnd.java", false);
        s.run("/cc2/d3/PropertyElseBegin.java", false);
        s.run("/cc2/d3/PropertyElseEnd.java", false);
        s.run("/cc2/d3/PropertyIfEnd.java", false);
    }

    @Test
    public void genFunc() {
        Simulator s = new Simulator();
        s.setWaitTime(4000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyFuncDef.java", false);
        s.run("/cc2/d3/PropertyFuncArgList.java", false);
        s.run("/cc2/d3/PropertyFuncArgDef.java", false);
        s.run("/cc2/d3/PropertyFunc.java", false);
    }

    @Test
    public void genPropertyField() {
        Simulator s = new Simulator();
        s.setWaitTime(4000);
        s.setRange(2, 100);
        s.run("/cc2/d3/PropertyField.java", false);
    }
}
