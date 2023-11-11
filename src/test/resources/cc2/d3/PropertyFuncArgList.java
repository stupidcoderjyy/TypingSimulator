package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.EnvBuilder;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyFuncArgList implements IProperty {
    $3$
    private static final EnvBuilder builder = EnvBuilder.INSTANCE;
    int count;

    $$
    //funcArgList → fieldDef
    private void reduce0(
            PropertyFieldDef p0) {
        $4$
        count = 1;
        builder.writeStore(p0.field.constPoolId);
        $$
    }

    //funcArgList → funcArgList , fieldDef
    private void reduce1(
            PropertyFuncArgList p0,
            PropertyTerminal p1,
            PropertyFieldDef p2) {
        $5$
        count = p0.count + 1;
        builder.writeStore(p2.field.constPoolId);
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        switch (p.id()) {
            case 11 -> reduce0(
                    (PropertyFieldDef)properties[0]
            );
            case 12 -> reduce1(
                    (PropertyFuncArgList)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyFieldDef)properties[2]
            );
        }
    }
}
$$
