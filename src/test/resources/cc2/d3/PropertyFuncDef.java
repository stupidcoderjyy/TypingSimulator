package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
import stupidcoder.compile.Field;
import stupidcoder.compile.tokens.TokenId;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyFuncDef implements IProperty {
    //funcDef → type id
    private void reduce0(
            PropertyType p0,
            PropertyTerminal p1) {
        $3$
        TokenId t1 = p1.getToken();
        EnvBuilder.INSTANCE.beginFunction(t1.lexeme, p0.type != Field.VOID_T);
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        reduce0(
            (PropertyType)properties[0],
            (PropertyTerminal)properties[1]
        );
    }
}
$$
