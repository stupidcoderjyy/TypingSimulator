package stupidcoder.runtime;

$1$
$2$
import java.util.Stack;
$$

public class Env {
    $3$
    public static final Env INSTANCE = new Env();
    public final Stack<StackFrame> frames = new Stack<>();
    public Function[] functions;
    public Function mainFunc;

    public void run() {
        $4$
        mainFunc.call(this);
        while (!frames.empty()) {
            $5$
            StackFrame frame = frames.peek();
            switch (frame.run(this)) {
                $6$
                case StackFrame.SUSPEND -> {}
                case StackFrame.RETURN_VOID -> frames.pop();
                case StackFrame.RETURN_OBJECT -> {
                    Object ret = frame.operands.pop();
                    frames.pop();
                    frames.peek().operands.push(ret);
                }
                $$
            }
            $$
        }
        $$
    }
    $$
}
$$
