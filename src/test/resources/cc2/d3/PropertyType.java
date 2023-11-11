package stupidcoder.compile.properties;

$1$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyType implements IProperty {
    $2$
    int type;
    boolean isArr;
    int arrDepth;

    $$
    //type → baseType
    private void reduce0(
            PropertyBaseType p0) {
        $3$
        type = p0.type;
        $$
    }

    //type → type [ ]
    private void reduce1(
            PropertyType p0,
            PropertyTerminal p1,
            PropertyTerminal p2) {
        $4$
        isArr = true;
        arrDepth = p0.arrDepth + 1;
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        switch (p.id()) {
            case 78 -> reduce0(
                    (PropertyBaseType)properties[0]
            );
            case 79 -> reduce1(
                    (PropertyType)properties[0],
                    (PropertyTerminal)properties[1],
                    (PropertyTerminal)properties[2]
            );
        }
    }
}
$$
