package stupidcoder.runtime;

$1$
import java.lang.reflect.Array;
import java.util.Stack;

public class StackFrame {
    $2$
    static final int RETURN_VOID = 0;
    static final int RETURN_OBJECT = 1;
    static final int SUSPEND = 2;
    private final byte[] opCodes;
    public final Stack<Object> operands = new Stack<>();
    private final Object[] tempVars;
    private int pos = 0;
    private final FuncDefined func;

    public StackFrame(FuncDefined func) {
        $3$
        this.func = func;
        this.opCodes = func.opCodes;
        this.tempVars = new Object[func.varTableSize];
        $$
    }

    public int run(Env env) {
        $4$
        while (true) {
            $5$
            switch (opCodes[pos++]) {
                $7$
                case Codes.CONST_I -> operands.push(nextInt());
                case Codes.DUP -> operands.push(operands.peek());
                case Codes.LOAD -> operands.push(tempVars[nextInt()]);
                case Codes.STORE -> tempVars[nextInt()] = operands.pop();
                case Codes.INVOKE -> {
                    env.functions[nextInt()].call(env);
                    return SUSPEND;
                }
                case Codes.ADD -> operands.push((int)operands.pop() + (int)operands.pop());
                case Codes.MINUS -> operands.push(-(int)operands.pop() + (int)operands.pop());
                case Codes.RETURN -> {
                    if (func.hasReturnValue()) {
                        return RETURN_OBJECT;
                    }
                    return RETURN_VOID;
                }
                case Codes.IF_LESS_EQUALS -> {
                    int i = nextInt();
                    if ((int) operands.pop() >= (int) operands.pop()) {
                        pos = i;
                    }
                }
                case Codes.IF_GREATER_EQUALS -> {
                    int i = nextInt();
                    if ((int) operands.pop() <= (int) operands.pop()) {
                        pos = i;
                    }
                }
                case Codes.IF_EQUALS -> {
                    int i = nextInt();
                    if ((int) operands.pop() == (int) operands.pop()) {
                        pos = i;
                    }
                }
                case Codes.GOTO -> pos = nextInt();
                case Codes.ARR_ELEMENT -> {
                    int i = (int) operands.pop();
                    operands.push(Array.get(operands.pop(), i));
                }
                case Codes.NEW_ARRAY_I -> operands.push(new int[nextInt()]);
                case Codes.NEW_ARRAY_A -> operands.push(new Object[nextInt()]);
                case Codes.ARR_SET -> {
                    Object data = operands.pop();
                    int i = (int) operands.pop();
                    Object arr = operands.pop();
                    Array.set(arr, i, data);
                }
                case Codes.ARR_SET_CONST -> {
                    Object arr = operands.pop();
                    Array.set(arr, (int) operands.pop(), operands.pop());
                    operands.push(arr);
                }
                case Codes.MULTI_NEW_ARRAY -> {
                    int dimensions = nextInt();
                    int[] args = new int[dimensions];
                    for (int i = dimensions - 1 ; i >= 0 ; i --) {
                        args[i] = (int) operands.pop();
                    }
                    operands.push(Array.newInstance(int.class, args));
                }
                case Codes.MULTIPLY -> operands.push((int) operands.pop() * (int) operands.pop());
                case Codes.DIVIDE -> {
                    int i1 = (int) operands.pop();
                    int i2 = (int) operands.pop();
                    operands.push(i2 / i1);
                }
                $$
            }
            $$
        }
        $$
    }
    $$
    $6$

    private int nextInt() {
        return ((opCodes[pos++] & 0xFF) << 24) |
                ((opCodes[pos++] & 0xFF) << 16) |
                ((opCodes[pos++] & 0xFF) << 8) |
                (opCodes[pos++] & 0xFF);
    }
    $$
}
$$