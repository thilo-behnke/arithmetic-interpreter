package lang.lexer;

import java.util.HashSet;
import java.util.Set;

public enum TokenType {
    PLUS, MINUS, MULTIPLY, DIVIDE,
    NUMBER, IDENTIFIER,
    ASSIGNMENT,
    LEFT_BRACKET, RIGHT_BRACKET,
    SQRT, LOG, SIN, COS,
    EOF;

    public static Set<TokenType> getArithmeticOperators() {
        Set<TokenType> operators = new HashSet<>();
        operators.add(PLUS);
        operators.add(MINUS);
        operators.add(MULTIPLY);
        operators.add(DIVIDE);
        return operators;
    }

    public boolean isArithmeticOperator() {
        return getArithmeticOperators().contains(this);
    }

    public static Set<TokenType> getFunctions() {
        Set<TokenType> functions = new HashSet<>();
        functions.add(SQRT);
        functions.add(LOG);
        functions.add(SIN);
        functions.add(COS);
        return functions;
    }

    public boolean isFunction() {
        return getFunctions().contains(this);
    }

    public boolean isAssignment() {
        return this.equals(ASSIGNMENT);
    }
}
