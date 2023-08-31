private List<String> nodeToToken    private final <Boolean> accepnew ArrayLpackage com.stupidcoder.cc.lex.core;

public interface IDfaSetter {
    void setAccepted(int i, String token);
    void setGoTo(int start, int input, int target);
    void setStartState(int i);
    void setDfaStatesCount(int count);
}
