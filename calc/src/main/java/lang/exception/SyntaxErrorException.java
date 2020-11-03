package lang.exception;

import lang.lexer.LexerState;

public class SyntaxErrorException extends RuntimeException {
    public SyntaxErrorException(String message, LexerState lexerState) {
        super("Syntax error for expression: \"" + lexerState.getExpression() + "\". Error: " + message + ". Encountered at index " + lexerState.getCurrentIndex() + ".");
    }
}
