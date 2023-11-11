package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
import stupidcoder.compile.tokens.TokenId;
import stupidcoder.compile.tokens.TokenInt;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyExpr implements IProperty {
    $3$
    private static final EnvBuilder builder = EnvBuilder.INSTANCE;
    $25$
    int arrDepth = 0;
    $$

    $$
    //expr → expr + expr
    private void reduce0(
            PropertyExpr p0,
            PropertyTerminal p1,
            PropertyExpr p2) {
        $4$
        builder.writeAdd();
        $$
    }

    //expr → expr - expr
    private void reduce1(
            PropertyExpr p0,
            PropertyTerminal p1,
            PropertyExpr p2) {
        $5$
        builder.writeMinus();
        $$
    }

    //expr → expr * expr
    private void reduce2(
            PropertyExpr p0,
            PropertyTerminal p1,
            PropertyExpr p2) {
        $6$
        builder.writeMultiply();
        $$
    }

    //expr → expr / expr
    private void reduce3(
            PropertyExpr p0,
            PropertyTerminal p1,
            PropertyExpr p2) {
        $7$
        builder.writeDivide();
        $$
    }

    //expr → expr eq expr
    private void reduce4(
            PropertyExpr p0,
            PropertyTerminal p1,
            PropertyExpr p2) {
        $8$
        int top = builder.getCodeSize();
        builder.writeIfEquals(top + 15);
        builder.writeConstInt(0);
        builder.writeGoTo(top + 20);
        builder.writeConstInt(1);
        $$
    }

    //expr → expr > expr
    private void reduce5(
            PropertyExpr p0,
            PropertyTerminal p1,
            PropertyExpr p2) {
        $9$
        int top = builder.getCodeSize();
        builder.writeIfLessEquals(top + 15);
        builder.writeConstInt(1);
        builder.writeGoTo(top + 20);
        builder.writeConstInt(0);
        $$
    }

    //expr → expr < expr
    private void reduce8(
            PropertyExpr p0,
            PropertyTerminal p1,
            PropertyExpr p2) {
        $10$
        int top = builder.getCodeSize();
        builder.writeIfGreaterEquals(top + 15);
        builder.writeConstInt(1);
        builder.writeGoTo(top + 20);
        builder.writeConstInt(0);
        $$
    }

    //expr → expr ge expr
    private void reduce9(
            PropertyExpr p0,
            PropertyTerminal p1,
            PropertyExpr p2) {
        $11$
        int top = builder.getCodeSize();
        builder.writeIfGreaterEquals(top + 15);
        builder.writeConstInt(0);
        builder.writeGoTo(top + 20);
        builder.writeConstInt(1);
        $$
    }

    //expr → expr le expr
    private void reduce10(
            PropertyExpr p0,
            PropertyTerminal p1,
            PropertyExpr p2) {
        $12$
        int top = builder.getCodeSize();
        builder.writeIfLessEquals(top + 15);
        builder.writeConstInt(0);
        builder.writeGoTo(top + 20);
        builder.writeConstInt(1);
        $$
    }

    //expr → ( expr )
    private void reduce11(
            PropertyTerminal p0,
            PropertyExpr p1,
            PropertyTerminal p2) {
    }

    //expr → + expr
    private void reduce12(
            PropertyTerminal p0,
            PropertyExpr p1) {
    }

    //expr → - expr
    private void reduce13(
            PropertyTerminal p0,
            PropertyExpr p1) {
        $13$
        builder.writeConstInt(-1);
        builder.writeMultiply();
        $$
    }

    //expr → id ( exprs )
    private void reduce14(
            PropertyTerminal p0,
            PropertyTerminal p1,
            PropertyExprs p2,
            PropertyTerminal p3) {
        $14$
        TokenId t0 = p0.getToken();
        builder.writeInvoke(t0.lexeme);
        $$
    }

    //expr → int
    private void reduce15(
            PropertyTerminal p0) {
        $15$
        TokenInt t0 = p0.getToken();
        builder.writeConstInt(t0.val);
        $$
    }

    //expr → $true
    private void reduce16(
            PropertyTerminal p0) {
        $16$
        builder.writeConstInt(1);
        $$
    }

    //expr → $false
    private void reduce17(
            PropertyTerminal p0) {
        $17$
        builder.writeConstInt(0);
        $$
    }

    //expr → var
    private void reduce19(
            PropertyVar p0) {
        $26$
        builder.popMark();
        if (p0.isArrType) {
            if (p0.accessedDimensions > 0) {
                builder.writeArrElement();
            } else {
                builder.writeLoad(p0.field.constPoolId);
            }
        } else {
            builder.writeLoad(p0.field.constPoolId);
        }
        $$
    }

    //expr → var inc
    private void reduce20(
            PropertyVar p0,
            PropertyTerminal p1) {
        $27$
        builder.popMark();
        builder.writeLoad(p0.field.constPoolId);
        builder.writeDuplicate();
        builder.writeConstInt(1);
        builder.writeAdd();
        builder.writeStore(p0.field.constPoolId);
        $$
    }

    //expr → var dec
    private void reduce21(
            PropertyVar p0,
            PropertyTerminal p1) {
        $28$
        builder.popMark();
        builder.writeLoad(p0.field.constPoolId);
        builder.writeDuplicate();
        builder.writeConstInt(-1);
        builder.writeAdd();
        builder.writeStore(p0.field.constPoolId);
        $$
    }

    //expr → inc var
    private void reduce22(
            PropertyTerminal p0,
            PropertyVar p1) {
        $29$
        builder.popMark();
        builder.writeLoad(p1.field.constPoolId);
        builder.writeConstInt(1);
        builder.writeAdd();
        builder.writeDuplicate();
        builder.writeStore(p1.field.constPoolId);
        $$
    }

    //expr → dec var
    private void reduce23(
            PropertyTerminal p0,
            PropertyVar p1) {
        $30$
        builder.popMark();
        builder.writeLoad(p1.field.constPoolId);
        builder.writeConstInt(-1);
        builder.writeAdd();
        builder.writeDuplicate();
        builder.writeStore(p1.field.constPoolId);
        $$
    }

    //expr → $new arrDef
    private void reduce24(
            PropertyTerminal p0,
            PropertyArrDef p1) {
        $31$
        builder.writeMultiNewArray(p1.dimensionCount);
        $$
    }
}
$$