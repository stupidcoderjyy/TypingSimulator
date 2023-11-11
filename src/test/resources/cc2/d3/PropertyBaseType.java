package stupidcoder.compile.properties;

$1$
$2$
import stupidcoder.compile.Field;
$$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyBaseType implements IProperty {
    $3$
    int type;

    $$
    //baseType → $int
    private void reduce0(
            PropertyTerminal p0) {
        $4$
        this.type = Field.INT_T;
        $$
    }

    //baseType → $bool
    private void reduce1(
            PropertyTerminal p0) {
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        switch (p.id()) {
            case 80 -> reduce0(
                    (PropertyTerminal)properties[0]
            );
            case 81 -> reduce1(
                    (PropertyTerminal)properties[0]
            );
        }
    }
}
$$
