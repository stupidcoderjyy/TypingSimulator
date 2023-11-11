package stupidcoder.runtime;

$1$
import java.util.Random;
import java.util.Stack;

public class FuncRand implements Function{
    $2$
    private static final Random random = new Random();

    @Override
    public void call(Env env) {
        $3$
        Stack<Object> operands = env.frames.peek().operands;
        operands.push(random.nextInt((int) operands.pop()));
        $$
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }
    $$
}
$$
