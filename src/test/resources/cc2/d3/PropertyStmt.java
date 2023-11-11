package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
import stupidcoder.compile.tokens.TokenId;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyStmt implements IProperty {
    $3$
    private final EnvBuilder builder = EnvBuilder.INSTANCE;

    $$
    //stmt → field
    private void reduce0(
            PropertyField p0) {
    }

    //stmt → $if ( expr ) ifCondEnd stmt ifEnd
    private void reduce1(
            PropertyTerminal p0,
            PropertyTerminal p1,
            PropertyExpr p2,
            PropertyTerminal p3,
            PropertyIfCondEnd p4,
            PropertyStmt p5,
            PropertyIfEnd p6) {
    }

    //stmt → $if ( expr ) ifCondEnd stmt $else elseBegin stmt elseEnd
    private void reduce2(
            PropertyTerminal p0,
            PropertyTerminal p1,
            PropertyExpr p2,
            PropertyTerminal p3,
            PropertyIfCondEnd p4,
            PropertyStmt p5,
            PropertyTerminal p6,
            PropertyElseBegin p7,
            PropertyStmt p8,
            PropertyElseEnd p9) {
    }

    //stmt → $while ( whileCondBegin expr ) whileCondEnd stmt whileEnd
    private void reduce3(
            PropertyTerminal p0,
            PropertyTerminal p1,
            PropertyWhileCondBegin p2,
            PropertyExpr p3,
            PropertyTerminal p4,
            PropertyWhileCondEnd p5,
            PropertyStmt p6,
            PropertyWhileEnd p7) {
    }

    //stmt → { stmts }
    private void reduce4(
            PropertyTerminal p0,
            PropertyStmts p1,
            PropertyTerminal p2) {
    }

    //stmt → $return expr ;
    private void reduce5(
            PropertyTerminal p0,
            PropertyExpr p1,
            PropertyTerminal p2) {
        $4$
        builder.writeReturn();
        $$
    }

    //stmt → $return ;
    private void reduce6(
            PropertyTerminal p0,
            PropertyTerminal p1) {
        $5$
        builder.writeReturn();
        $$
    }

    //stmt → id ( exprs ) ;
    private void reduce7(
            PropertyTerminal p0,
            PropertyTerminal p1,
            PropertyExprs p2,
            PropertyTerminal p3,
            PropertyTerminal p4) {
        $6$
        TokenId t0 = p0.getToken();
        builder.writeInvoke(t0.lexeme);
        $$
    }

    //stmt → var inc ;
    private void reduce8(
            PropertyVar p0,
            PropertyTerminal p1,
            PropertyTerminal p2) {
        $7$
        builder.popMark();
        builder.writeLoad(p0.field.constPoolId);
        builder.writeConstInt(1);
        builder.writeAdd();
        builder.writeStore(p0.field.constPoolId);
        $$
    }

    //stmt → var dec ;
    private void reduce9(
            PropertyVar p0,
            PropertyTerminal p1,
            PropertyTerminal p2) {
        $8$
        builder.popMark();
        builder.writeLoad(p0.field.constPoolId);
        builder.writeConstInt(-1);
        builder.writeAdd();
        builder.writeStore(p0.field.constPoolId);
        $$
    }

    //stmt → var assignInc expr ;
    private void reduce10(
            PropertyVar p0,
            PropertyTerminal p1,
            PropertyExpr p2,
            PropertyTerminal p3) {
    }

    //stmt → var assignDec expr ;
    private void reduce11(
            PropertyVar p0,
            PropertyTerminal p1,
            PropertyExpr p2,
            PropertyTerminal p3) {
    }

    //stmt → $continue ;
    private void reduce12(
            PropertyTerminal p0,
            PropertyTerminal p1) {
        $9$
        builder.pushContinue();
        $$
    }

    //stmt → $break ;
    private void reduce13(
            PropertyTerminal p0,
            PropertyTerminal p1) {
        $10$
        builder.pushBreak();
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        switch (p.id()) {
            case 17 -> reduce0(
                    (PropertyField)properties[0]
            );
            case 18 -> reduce1(
                    (PropertyTerminal)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyExpr)properties[2],
                    (PropertyTerminal)properties[3],
                    (PropertyIfCondEnd)properties[4],
                    (PropertyStmt)properties[5],
                    (PropertyIfEnd)properties[6]
            );
            case 19 -> reduce2(
                    (PropertyTerminal)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyExpr)properties[2],
                    (PropertyTerminal)properties[3],
                    (PropertyIfCondEnd)properties[4],
                    (PropertyStmt)properties[5],
                    (PropertyTerminal)properties[6],
                    (PropertyElseBegin)properties[7],
                    (PropertyStmt)properties[8],
                    (PropertyElseEnd)properties[9]
            );
            case 20 -> reduce3(
                    (PropertyTerminal)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyWhileCondBegin)properties[2],
                    (PropertyExpr)properties[3],
                    (PropertyTerminal)properties[4],
                    (PropertyWhileCondEnd)properties[5],
                    (PropertyStmt)properties[6],
                    (PropertyWhileEnd)properties[7]
            );
            case 21 -> reduce4(
                    (PropertyTerminal)properties[0],
                    (PropertyStmts)properties[1],
                    (PropertyTerminal)properties[2]
            );
            case 22 -> reduce5(
                    (PropertyTerminal)properties[0],
                    (PropertyExpr)properties[1],
                    (PropertyTerminal)properties[2]
            );
            case 23 -> reduce6(
                    (PropertyTerminal)properties[0],
                    (PropertyTerminal)properties[1]
            );
            case 24 -> reduce7(
                    (PropertyTerminal)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyExprs)properties[2],
                    (PropertyTerminal)properties[3],
                    (PropertyTerminal)properties[4]
            );
            case 25 -> reduce8(
                    (PropertyVar)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyTerminal)properties[2]
            );
            case 26 -> reduce9(
                    (PropertyVar)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyTerminal)properties[2]
            );
            case 27 -> reduce10(
                    (PropertyVar)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyExpr)properties[2],
                    (PropertyTerminal)properties[3]
            );
            case 28 -> reduce11(
                    (PropertyVar)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyExpr)properties[2],
                    (PropertyTerminal)properties[3]
            );
            case 29 -> reduce12(
                    (PropertyTerminal)properties[0],
                    (PropertyTerminal)properties[1]
            );
            case 30 -> reduce13(
                    (PropertyTerminal)properties[0],
                    (PropertyTerminal)properties[1]
            );
        }
    }
}
$$
