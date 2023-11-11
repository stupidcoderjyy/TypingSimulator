package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
import stupidcoder.compile.Field;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.util.compile.property.PropertyTerminal;
import stupidcoder.compile.common.syntax.IProperty;

public class PropertyFieldDef implements IProperty {
    $3$
    private static final EnvBuilder builder = EnvBuilder.INSTANCE;
    Field field;

    $$
    //fieldDef → type fieldName
    private void reduce0(
            PropertyType p0,
            PropertyFieldName p1) {
        $4$
        if (p0.isArr) {
            field = builder.defArrField(p1.name, p0.arrDepth);
        } else {
            field = builder.defField(p1.name, p0.type);
        }
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        reduce0(
            (PropertyType)properties[0],
            (PropertyFieldName)properties[1]
        );
    }
}
$$
