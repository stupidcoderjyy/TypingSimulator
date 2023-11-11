package stupidcoder.core.scriptloader.properties;

$1$
$2$
import stupidcoder.core.scriptloader.ScriptLoader;
import stupidcoder.core.scriptloader.tokens.TokenId;
import stupidcoder.core.scriptloader.tokens.TokenTerminal;
import stupidcoder.lex.NFARegexParser;
import stupidcoder.syntax.SyntaxLoader;
import stupidcoder.util.compile.symbol.DefaultSymbols;
$$
import stupidcoder.util.compile.property.IProperty;
import stupidcoder.util.compile.property.PropertyTerminal;
import stupidcoder.util.compile.Production;

public class PropertySymbol implements IProperty {
    $3$
    private final SyntaxLoader loader;
    private final ScriptLoader env;
    private final NFARegexParser parser;
    private static int terminalId = 128;

    public PropertySymbol(SyntaxLoader loader, ScriptLoader env, NFARegexParser parser) {
        $4$
        this.loader = loader;
        this.env = env;
        this.parser = parser;
        $$
    }

    $$
    //symbol → id
    private void reduce0(
            PropertyTerminal p0) {
        $6$
        TokenId t0 = p0.getToken();
        loader.addNonTerminal(t0.lexeme);
        $$
    }

    //symbol → terminal priorityT
    private void reduce1(
            PropertyTerminal p0,
            PropertyPriorityT p1) {
        $7$
        TokenTerminal t0 = p0.getToken();
        switch (t0.terminalType) {
            $8$
            case TokenTerminal.EPSILON -> loader.add(DefaultSymbols.EPSILON);
            case TokenTerminal.NORMAL -> {
                $9$
                if (env.nameToTerminalId.containsKey(t0.lexeme)) {
                    loader.addTerminal(t0.lexeme, env.nameToTerminalId.get(t0.lexeme));
                } else {
                    int id = terminalId++;
                    loader.addTerminal(t0.lexeme, id);
                    env.nameToTerminalId.put(t0.lexeme, id);
                }
                $$
            }
            $$
            $10$
            case TokenTerminal.SINGLE -> {
                $11$
                parser.registerSingle(t0.ch);
                loader.addTerminal(t0.ch);
                $$
            }
            $$
            $12$
            case TokenTerminal.KEY_WORD -> env.keyWords.compute(t0.lexeme, (k, v) -> {
                $13$
                if (v == null) {
                    v = terminalId++;
                }
                loader.addTerminal('$' + k, v);
                return v;
                $$
            });
            $$
            $14$
            case TokenTerminal.EOF -> loader.add(DefaultSymbols.EOF);
            $$
        }
        $$
        $15$
        if (p1.val != 0) {
            loader.setPriority(p1.val);
        }
        $$
    }

    @Override
    public void onReduced(Production p, IProperty... properties) {
        switch (p.id()) {
            case 18 -> reduce0(
                    (PropertyTerminal)properties[0]
            );
            case 19 -> reduce1(
                    (PropertyTerminal)properties[0],
                    (PropertyPriorityT)properties[1]
            );
        }
    }
}
$$