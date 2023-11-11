package cc2.d3;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
import stupidcoder.compile.Field;
import stupidcoder.compile.tokens.TokenId;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyFieldName implements IProperty {
    $3$
    private static final EnvBuilder builder = EnvBuilder.INSTANCE;
    String name;
    Field field;

    $$
    //fieldName → id
    private void reduce0(
            PropertyTerminal p0) {
        $4$
        TokenId t0 = p0.getToken();
        name = t0.lexeme;
        field = builder.getField(name);
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        reduce0(
            (PropertyTerminal)properties[0]
        );
    }
}
$$