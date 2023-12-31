package stupidcoder.core.scriptloader.tokens;

$1$
$2$
import stupidcoder.core.LexerBuilder;
import stupidcoder.util.Config;
$$
import stupidcoder.util.compile.token.IToken;
import stupidcoder.util.input.CompileException;
import stupidcoder.util.input.CompilerInput;

public class TokenTerminal implements IToken {
    $3$
    private static final boolean keyWordEnabled = Config.getBool(LexerBuilder.KEY_WORD_TOKEN);
    public static final int SINGLE = 0;
    public static final int EPSILON = 1;
    public static final int NORMAL = 2;
    public static final int KEY_WORD = 3;
    public static final int EOF = 4;
    $$
    public String lexeme;
    $4$
    public int terminalType;
    public char ch;
    $$

    @Override
    public int type() {
        return 135;
    }

    @Override
    public IToken onMatched(String lexeme, CompilerInput input) throws CompileException {
        $5$
        if (lexeme.charAt(0) == '@') {
            $6$
            switch (lexeme.charAt(1)) {
                $7$
                case '~' -> {
                    $8$
                    this.terminalType = EPSILON;
                    this.lexeme = "ε";
                    $$
                }
                case '$' -> {
                    $9$
                    if (lexeme.charAt(2) == '$') {
                        this.terminalType = EOF;
                        this.lexeme = lexeme;
                    } else if (keyWordEnabled) {
                        this.terminalType = KEY_WORD;
                        this.lexeme = lexeme.substring(2);
                    } else {
                        throw input.errorMarkToForward("keyword token disabled");
                    }
                    $$
                }
                default -> {
                    $10$
                    this.terminalType = NORMAL;
                    this.lexeme = lexeme.substring(1);
                    $$
                }
                $$
            }
            $$
        } else {
            $11$
            this.terminalType = SINGLE;
            this.ch = lexeme.charAt(1);
            this.lexeme = String.valueOf(ch);
            $$
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