package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyElseBegin implements IProperty {
    $3$
    private final EnvBuilder builder = EnvBuilder.INSTANCE;

    $$
    //elseBegin → ε
    private void reduce0(
            PropertyTerminal p0) {
        $4$
        builder.retract();
        builder.writeIfEquals(builder.getCodeSize() + 5);
        builder.moveToEnd();
        builder.markAndSkip(5); // goto <int>
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
