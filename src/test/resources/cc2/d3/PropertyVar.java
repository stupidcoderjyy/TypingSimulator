package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
import stupidcoder.compile.Field;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyVar implements IProperty {
    $3$
    private static final EnvBuilder builder = EnvBuilder.INSTANCE;
    Field field;
    boolean isArrType;
    int accessedDimensions;
    int curDepth;

    $$
    //var → fieldName
    private void reduce0(
            PropertyFieldName p0) {
        $4$
        field = p0.field;
        int dimensions = p0.field.type & 0xFFFF;
        builder.mark();
        if (dimensions != 0) {
            isArrType = true;
            accessedDimensions = 0;
            curDepth = dimensions;
        }
        $$
    }

    //var → varArrBegin expr ]
    private void reduce1(
            PropertyVarArrBegin p0,
            PropertyExpr p1,
            PropertyTerminal p2) {
        $5$
        field = p0.field;
        isArrType = true;
        accessedDimensions = p0.accessedDimensions;
        curDepth = p0.curDepth;
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        switch (p.id()) {
            case 66 -> reduce0(
                    (PropertyFieldName)properties[0]
            );
            case 67 -> reduce1(
                    (PropertyVarArrBegin)properties[0],
                    (PropertyExpr)properties[1],
                    (PropertyTerminal)properties[2]
            );
        }
    }
}
$$
