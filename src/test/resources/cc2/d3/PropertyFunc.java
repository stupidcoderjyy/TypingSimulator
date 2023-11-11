package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyFunc implements IProperty {
    //func → funcDef ( funcArgDef ) { stmts }
    private void reduce0(
            PropertyFuncDef p0,
            PropertyTerminal p1,
            PropertyFuncArgDef p2,
            PropertyTerminal p3,
            PropertyTerminal p4,
            PropertyStmts p5,
            PropertyTerminal p6) {
        $3$
        EnvBuilder.INSTANCE.endFunction(p2.argCount);
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        reduce0(
            (PropertyFuncDef)properties[0],
            (PropertyTerminal)properties[1],
            (PropertyFuncArgDef)properties[2],
            (PropertyTerminal)properties[3],
            (PropertyTerminal)properties[4],
            (PropertyStmts)properties[5],
            (PropertyTerminal)properties[6]
        );
    }
}
$$
