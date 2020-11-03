package lang.handler;

import lang.lexer.Token;
import lang.lexer.TokenType;

public class MathOperationHandler {
    public static double handleOperation(Token<Object> left, Token<Object> operator, Token<Object> right) {
        switch(operator.getType()) {
            case PLUS:
                return (double) left.getValue() + (double) right.getValue();
            case MINUS:
                return (double) left.getValue() - (double) right.getValue();
            case MULTIPLY:
                return (double) left.getValue() * (double) right.getValue();
            case DIVIDE:
                if((double) right.getValue() == 0.0d) {
                    throw new ArithmeticException("division by 0");
                }
                return (double) left.getValue() / (double) right.getValue();
            default:
                throw new UnsupportedOperationException("Unsupported token type for math operation: " + operator.getType());
        }
    }

    public static double handleOperation(double left, Token<Object> operator, double right) {
        switch(operator.getType()) {
            case PLUS:
                return left + right;
            case MINUS:
                return left - right;
            case MULTIPLY:
                return left * right;
            case DIVIDE:
                if(right == 0.0d) {
                    throw new ArithmeticException("division by 0");
                }
                return left / right;
            default:
                throw new UnsupportedOperationException("Unsupported token type for math operation: " + operator.getType());
        }
    }

    public static double handleFunction(TokenType functionTokenType, double input) {
        switch(functionTokenType) {
            case SQRT:
                return Math.sqrt(input);
            case LOG:
                return Math.log(input);
            case COS:
                return Math.cos(input);
            case SIN:
                return Math.sin(input);
            default:
                throw new IllegalArgumentException("Can't handle token type " + functionTokenType + " as a function token type!");
        }
    }
}
