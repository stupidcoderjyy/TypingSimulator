package stupidcoder.compile.syntax;

$1$
public interface ISyntaxAnalyzerSetter {
    $2$
    void setActionShift(int from, int to, int inputTerminal);
    void setActionReduce(int state, int forward, int productionId);
    void setActionAccept(int state, int forward);
    void setGoto(int from, int to, int inputNonTerminal);
    void setStatesCount(int count);
    void setOthers(SyntaxLoader loader);
    $$
}
$$
