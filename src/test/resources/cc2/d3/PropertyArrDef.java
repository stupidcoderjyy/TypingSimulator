package stupidcoder.compile.properties;

$1$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyArrDef implements IProperty {
    $2$
    int dimensionCount;

    $$
    //arrDef → baseType
    private void reduce0(
            PropertyBaseType p0) {
        $3$
        dimensionCount = 0;
        $$
    }

    //arrDef → arrDef [ expr ]
    private void reduce1(
            PropertyArrDef p0,
            PropertyTerminal p1,
            PropertyExpr p2,
            PropertyTerminal p3) {
        $4$
        dimensionCount = p0.dimensionCount + 1;
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        switch (p.id()) {
            case 68 -> reduce0(
                    (PropertyBaseType)properties[0]
            );
            case 69 -> reduce1(
                    (PropertyArrDef)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyExpr)properties[2],
                    (PropertyTerminal)properties[3]
            );
        }
    }
}
$$