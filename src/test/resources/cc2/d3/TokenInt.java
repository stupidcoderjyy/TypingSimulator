package stupidcoder.compile.tokens;

$1$
import stupidcoder.compile.common.token.IToken;
import stupidcoder.util.input.CompileException;
import stupidcoder.util.input.CompilerInput;

public class TokenInt implements IToken {
    public String lexeme;
    $2$
    public int val;

    $$
    @Override
    public int type() {
        return 144;
    }

    @Override
    public IToken onMatched(String lexeme, CompilerInput input) throws CompileException {
        this.lexeme = lexeme;
        $3$
        if (lexeme.startsWith("0x")) {
            this.val = Integer.parseInt(lexeme.substring(2), 16);
        } else {
            this.val = Integer.parseInt(lexeme);
        }
        $$
        return this;
    }

    @Override
    public String toString() {
        return lexeme;
    }
}
$$