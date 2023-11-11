package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyField implements IProperty {
    $3$
    private static final EnvBuilder builder = EnvBuilder.INSTANCE;

    $$
    //field → fieldDef ;
    private void reduce0(
            PropertyFieldDef p0,
            PropertyTerminal p1) {
    }

    //field → fieldDef = expr ;
    private void reduce1(
            PropertyFieldDef p0,
            PropertyTerminal p1,
            PropertyExpr p2,
            PropertyTerminal p3) {
        $4$
        builder.writeStore(p0.field.constPoolId);
        $$
    }

    //field → var = expr ;
    private void reduce2(
            PropertyVar p0,
            PropertyTerminal p1,
            PropertyExpr p2,
            PropertyTerminal p3) {
        $5$
        builder.popMark();
        if (p0.isArrType && p0.accessedDimensions > 0) {
            builder.writeArrSet();
        } else {
            builder.writeStore(p0.field.constPoolId);
        }
        builder.moveToEnd();
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        switch (p.id()) {
            case 37 -> reduce0(
                    (PropertyFieldDef)properties[0],
                    (PropertyTerminal)properties[1]
            );
            case 38 -> reduce1(
                    (PropertyFieldDef)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyExpr)properties[2],
                    (PropertyTerminal)properties[3]
            );
            case 39 -> reduce2(
                    (PropertyVar)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyExpr)properties[2],
                    (PropertyTerminal)properties[3]
            );
        }
    }
}
$$
