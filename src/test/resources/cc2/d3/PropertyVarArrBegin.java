package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
import stupidcoder.compile.Field;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyVarArrBegin implements IProperty {
    $3$
    private static final EnvBuilder builder = EnvBuilder.INSTANCE;
    Field field;
    int accessedDimensions;
    int curDepth;

    $$
    //varArrBegin → var [
    private void reduce0(
            PropertyVar p0,
            PropertyTerminal p1) {
        $4$
        builder.popMark();
        field = p0.field;
        if (p0.accessedDimensions == 0) {
            builder.writeLoad(field.constPoolId);
        }
        accessedDimensions = p0.accessedDimensions + 1;
        curDepth = p0.curDepth - 1;
        builder.mark();
        if (accessedDimensions > 1) {
            builder.writeArrElement();
        }
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        reduce0(
                (PropertyVar)properties[0],
                (PropertyTerminal)properties[1]
        );
    }
}
$$