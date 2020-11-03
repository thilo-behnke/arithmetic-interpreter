package lang.lexer;

import lang.utils.RegexUtils;
import lang.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lang.utils.LanguageUtils.matchReservedKeyword;

public class Lexer {

    private String expression;
    private List<String> tokens;
    private String currentChar;
    private int currentIndex;

    public Lexer() {
        initialize();
    }

    private void initialize() {
        tokens = new ArrayList<>();
        currentChar = null;
        currentIndex = 0;
    }

    public void setExpression(String expression) {
        initialize();
        this.expression = expression;
        tokens = Utils.explode(expression);
        currentChar = tokens.get(0);
    }

    public Token<Object> getNextToken() {
        // Whitespaces are ignored, just consume them.
        readWhitespace();

        if (this.currentIndex >= tokens.size()) {
            return new Token<>(TokenType.EOF, null);
        }


        if (RegexUtils.matchNumber(currentChar)) {
            Double doubleValue = readDouble();
            return new Token<>(TokenType.NUMBER, doubleValue);
        }

        if (RegexUtils.matchIdentifier(currentChar)) {
            String identifier = readIdentifier();
            Optional<TokenType> reservedKeyword = matchReservedKeyword(identifier);
            return reservedKeyword
                    .<Token<Object>>map(tokenType -> new Token<>(tokenType, tokenType.name().toLowerCase()))
                    .orElseGet(() -> new Token<>(TokenType.IDENTIFIER, identifier));
        }

        if (RegexUtils.matchOperator(currentChar)) {
            switch (currentChar) {
                case "+":
                    readNextChar();
                    return new Token<>(TokenType.PLUS, "+");
                case "-":
                    readNextChar();
                    return new Token<>(TokenType.MINUS, "-");
                case "*":
                    readNextChar();
                    return new Token<>(TokenType.MULTIPLY, "*");
                case "/":
                    readNextChar();
                    return new Token<>(TokenType.DIVIDE, "/");
                case "(":
                    readNextChar();
                    return new Token<>(TokenType.LEFT_BRACKET, "(");
                case ")":
                    readNextChar();
                    return new Token<>(TokenType.RIGHT_BRACKET, ")");
                case "=":
                    readNextChar();
                    return new Token<>(TokenType.ASSIGNMENT, "=");
            }
        }

        throw new UnsupportedOperationException("Unknown token encountered: " + currentChar);
    }

    private void readNextChar() {
        currentIndex++;
        currentChar = currentIndex < tokens.size() ? tokens.get(currentIndex) : null;
    }

    private void readWhitespace() {
        while(currentChar != null && currentChar.equals(" ")) {
            readNextChar();
        }
    }

    private double readDouble() {
        StringBuilder value = new StringBuilder();
        while (currentChar != null && (RegexUtils.matchNumber(currentChar) || RegexUtils.matchDelimiter(currentChar))) {
            value.append(currentChar);
            readNextChar();
        }
        String numberString = value.toString();
        double number = Double.parseDouble(numberString);
        if (number > 0 && numberString.startsWith("0")) {
            throw new UnsupportedOperationException("Invalid number " + numberString + " provided. Numbers other than 0 must not start with a 0!");
        }
        return number;
    }

    private String readIdentifier() {
        StringBuilder value = new StringBuilder();
        int index = 0;
        while (currentChar != null && (RegexUtils.matchIdentifier(currentChar) || index > 0 && RegexUtils.matchNumber(currentChar))) {
            value.append(currentChar);
            readNextChar();
            index++;
        }
        return value.toString();
    }

    public LexerState getState() {
        return new LexerState(expression, currentChar, currentIndex);
    }
}
