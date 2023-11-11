package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyWhileEnd implements IProperty {
    $3$
    private final EnvBuilder builder = EnvBuilder.INSTANCE;

    $$
    //whileEnd → ε
    private void reduce0(
            PropertyTerminal p0) {
        $4$
        builder.retract();
        int loopEnd = builder.getCodeSize() + 5;
        builder.writeIfEquals(loopEnd);
        builder.moveToEnd();
        int condBegin = builder.popMark();
        builder.writeGoTo(condBegin);
        builder.leaveLoop(condBegin, loopEnd);
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