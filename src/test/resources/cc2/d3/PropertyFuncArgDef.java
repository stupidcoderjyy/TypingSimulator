package stupidcoder.compile.properties;

$1$
import stupidcoder.compile.common.Production;
import stupidcoder.compile.common.syntax.IProperty;
import stupidcoder.compile.common.syntax.PropertyTerminal;

public class PropertyFuncArgDef implements IProperty {
    $2$
    int argCount;

    $$
    //funcArgDef → ε
    private void reduce0(
            PropertyTerminal p0) {
    }

    //funcArgDef → funcArgList
    private void reduce1(
            PropertyFuncArgList p0) {
        $3$
        argCount = p0.count;
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        switch (p.id()) {
            case 9 -> reduce0(
                    (PropertyTerminal)properties[0]
            );
            case 10 -> reduce1(
                    (PropertyFuncArgList)properties[0]
            );
        }
    }
}
$$
