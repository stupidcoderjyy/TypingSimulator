package stupidcoder.runtime;

$1$
import java.util.Arrays;

public class FuncPrintln implements Function{
    $2$
    @Override
    public void call(Env env) {
        $3$
        Object val = env.frames.peek().operands.pop();
        if (val.getClass().isArray()) {
            System.out.println(getArrString(new StringBuilder(), val));
        } else {
            System.out.println(val);
        }
        $$
    }

    private StringBuilder getArrString(StringBuilder sb, Object o) {
        $4$
        if (o instanceof Object[] arr) {
            sb.append("[");
            if (arr.length == 0) {
                sb.append("]");
                return sb;
            }
            for (int i = 0; i < arr.length - 1; i++) {
                getArrString(sb, arr[i]);
                sb.append(",");
            }
            getArrString(sb, arr[arr.length - 1]);
            sb.append("]");
        } else if (o instanceof int[] arr) {
            sb.append(Arrays.toString(arr));
        }
        return sb;
        $$
    }

    @Override
    public boolean hasReturnValue() {
        return false;
    }
    $$
}
$$