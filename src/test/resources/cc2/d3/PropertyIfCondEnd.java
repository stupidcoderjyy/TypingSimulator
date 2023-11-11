package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyIfCondEnd implements IProperty {
    $3$
    private final EnvBuilder builder = EnvBuilder.INSTANCE;

    $$
    //ifEnd → ε
    private void reduce0(
            PropertyTerminal p0) {
        $4$
        builder.writeConstInt(0);
        builder.markAndSkip(5); // if_eq <int>
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