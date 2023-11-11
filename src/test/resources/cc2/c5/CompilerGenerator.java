package stupidcoder.core;

$1$
import stupidcoder.core.scriptloader.Lexer;
import stupidcoder.core.scriptloader.ScriptLoader;
import stupidcoder.lex.DFABuilder;
import stupidcoder.lex.NFARegexParser;
import stupidcoder.syntax.LRGroupBuilder;
import stupidcoder.syntax.SyntaxLoader;
import stupidcoder.util.Config;
import stupidcoder.util.generate.project.java.JProjectBuilder;
import stupidcoder.util.input.CompilerInput;

public class CompilerGenerator {
    public static void gen(String scriptPath, String rootPkg) {
        $2$
        try (CompilerInput input = CompilerInput.fromResource(Config.resourcePath(scriptPath))){
            $3$
            JProjectBuilder builder = new JProjectBuilder("scripts/compile", rootPkg);
            SyntaxLoader syntaxLoader = new SyntaxLoader();
            ScriptLoader scriptLoader = new ScriptLoader(syntaxLoader, new NFARegexParser());
            scriptLoader.run(new Lexer(input));
            builder.addAdapter(new LexerBuilder(scriptLoader));
            builder.addAdapter(new SyntaxAnalyzerBuilder(syntaxLoader));
            builder.excludePkg("template");
            builder.gen();
            $$
        } catch (Exception e) {
            e.printStackTrace();
        }
        $$
    }
    $4$

    public static void gen(String scriptPath) {
        gen(scriptPath, Config.getString(JProjectBuilder.FRIEND_PKG_PREFIX));
    }

    public static void enableSyntaxDebugInfo() {
        Config.set(LRGroupBuilder.SYNTAX_DEBUG_INFO, true);
    }

    public static void enableSyntaxConflictInfo() {
        Config.set(LRGroupBuilder.SYNTAX_CONFLICT_INFO, true);
    }

    public static void enableLexerDebugInfo() {
        Config.set(DFABuilder.LEXER_DEBUG_INFO, true);
    }

    public static void enableCompressedArr() {
        Config.set(LexerBuilder.USE_COMPRESSED_ARR, true);
    }

    public static void enableKeyWordToken() {
        Config.set(LexerBuilder.KEY_WORD_TOKEN, true);
    }
    $$
}
$$