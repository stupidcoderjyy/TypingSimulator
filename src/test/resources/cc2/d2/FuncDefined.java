package stupidcoder.runtime;

$1$
public class FuncDefined implements Function{
    $2$
    final byte[] opCodes;
    final int varTableSize;
    final int argCount;
    final boolean hasReturnVal;
    private final String name;

    public FuncDefined(String name, byte[] opCodes, int varTableSize, int argCount, boolean hasReturnVal) {
        $3$
        this.opCodes = opCodes;
        this.varTableSize = varTableSize;
        this.argCount = argCount;
        this.name = name;
        this.hasReturnVal = hasReturnVal;
        $$
    }

    @Override
    public void call(Env env) {
        $4$
        StackFrame f = new StackFrame(this);
        if (!env.frames.empty()) {
            StackFrame peek = env.frames.peek();
            for (int i = 0; i < argCount; i++) {
                f.operands.push(peek.operands.pop());
            }
        }
        env.frames.push(f);
        $$
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean hasReturnValue() {
        return hasReturnVal;
    }
    $$
}
$$
