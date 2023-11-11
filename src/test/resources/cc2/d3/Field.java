package stupidcoder.compile;

$1$
public class Field {
    public static final int
            VOID_T = 0x00000,
            INT_T = 0x10000,
            ARR_T = 0x50000;
    public final int type;
    public final String name;
    public final int constPoolId;

    public Field(String name, int typeOrDepth, int constPoolId, boolean isArr) {
        $2$
        this.type = isArr ? (ARR_T | typeOrDepth) : typeOrDepth;
        this.name = name;
        this.constPoolId = constPoolId;
        $$
    }

    @Override
    public int hashCode() {
        $3$
        return type;
        $$
    }

    @Override
    public boolean equals(Object obj) {
        $4$
        if (obj instanceof Field f) {
            return f.type == type && f.name.equals(name);
        }
        return false;
        $$
    }
}
$$