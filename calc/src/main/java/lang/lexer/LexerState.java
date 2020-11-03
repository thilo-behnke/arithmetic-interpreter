package lang.lexer;

public class LexerState {
    String expression;
    String currentChar;
    int currentIndex;

    public LexerState(String expression, String currentChar, int currentIndex) {
        this.expression = expression;
        this.currentChar = currentChar;
        this.currentIndex = currentIndex;
    }

    public String getExpression() {
        return expression;
    }

    public String getCurrentChar() {
        return currentChar;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
