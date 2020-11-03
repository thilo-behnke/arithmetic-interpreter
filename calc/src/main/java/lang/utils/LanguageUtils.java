package lang.utils;

import lang.lexer.TokenType;

import java.util.Optional;

public class LanguageUtils {
    public static Optional<TokenType> matchReservedKeyword(String word) {
        switch (word) {
            case "sqrt":
                return Optional.of(TokenType.SQRT);
            case "log":
                return Optional.of(TokenType.LOG);
            case "sin":
                return Optional.of(TokenType.SIN);
            case "cos":
                return Optional.of(TokenType.COS);
            default:
                return Optional.empty();
        }
    }
}
