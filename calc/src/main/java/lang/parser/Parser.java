package lang.parser;

import lang.exception.NoBindingForVariableException;
import lang.exception.SyntaxErrorException;
import lang.lexer.Lexer;
import lang.lexer.LexerState;
import lang.lexer.Token;
import lang.lexer.TokenType;
import lang.parser.ast.ASTNode;

import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {

    private final Lexer lexer;
    private final Map<String, Double> bindings = new LinkedHashMap<>();

    private Token<Object> currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public void setExpression(String expression) {
        lexer.setExpression(expression);
        currentToken = lexer.getNextToken();
    }

    public ASTNode parse() {
        ASTNode result = evalExpression(null);
        if (!currentToken.getType().equals(TokenType.EOF)) {
            LexerState lexerState = lexer.getState();
            throw new SyntaxErrorException("Evaluation is finished, but EOF not yet reached.", lexerState);
        }
        return result;
    }

    private ASTNode evalExpression(ASTNode leftNode) {
        if(leftNode == null) {
            leftNode = evalTerm(null);
        }

        Token<Object> operator = currentToken;

        if(!operator.getType().isArithmeticOperator()) {
            return leftNode;
        }

        if(operator.getType().equals(TokenType.PLUS) || operator.getType().equals(TokenType.MINUS)) {
            consumeOperator(operator);
            ASTNode rightNode = evalTerm(null);
            ASTNode node = ASTNode.node(operator, leftNode, rightNode);
            return evalExpression(node);
        }

        return leftNode;
    }

    private ASTNode evalTerm(ASTNode leftNode) {
        if(leftNode == null) {
            leftNode = evalFactor();
        }

        Token<Object> operator = currentToken;

        if(operator.getType().equals(TokenType.MULTIPLY) || operator.getType().equals(TokenType.DIVIDE)) {
            consumeOperator(operator);
            ASTNode rightNode = evalFactor();

            ASTNode node = ASTNode.node(operator, leftNode, rightNode);
            return evalTerm(node);
        }

        return leftNode;
    }

    private ASTNode evalFactor() {
        Token<Object> token = currentToken;
        switch (currentToken.getType()) {
            case NUMBER:
                consume(TokenType.NUMBER);
                return ASTNode.leaf(token);
            case IDENTIFIER:
                consume(TokenType.IDENTIFIER);
                if (currentToken.getType().isAssignment()) {
                    Token<Object> assignmentToken = currentToken;
                    consume(TokenType.ASSIGNMENT);
                    ASTNode assignmentNode = evalExpression(null);
                    return ASTNode.node(assignmentToken, ASTNode.leaf(token), assignmentNode);
                }
                return ASTNode.leaf(token);
            case LEFT_BRACKET:
                consume(TokenType.LEFT_BRACKET);
                ASTNode innerExpressionNode = evalExpression(null);
                consume(TokenType.RIGHT_BRACKET);
                return innerExpressionNode;
            case SQRT:
            case LOG:
            case SIN:
            case COS:
                consume(currentToken.getType());
                ASTNode expressionNode = evalExpression(null);
                return ASTNode.node(token, expressionNode);
            case MINUS:
                consume(TokenType.MINUS);
                ASTNode node = evalExpression(null);
                return ASTNode.node(token, node);
            default:
                throw new UnsupportedOperationException("Can't factor for token type: " + token.getType());
        }
    }

    private Token<Object> getBindingValue(Token<Object> token) {
        Double bindingsValue = bindings().get((String) token.getValue());
        if (bindingsValue == null) {
            throw new NoBindingForVariableException(token.getValue().toString());
        }
        return new Token<>(TokenType.NUMBER, bindingsValue);
    }

    private void consumeOperator(Token<Object> operator) {
        switch (operator.getType()) {
            case PLUS:
                consume(TokenType.PLUS);
                break;
            case MINUS:
                consume(TokenType.MINUS);
                break;
            case MULTIPLY:
                consume(TokenType.MULTIPLY);
                break;
            case DIVIDE:
                consume(TokenType.DIVIDE);
                break;
            case ASSIGNMENT:
                consume(TokenType.ASSIGNMENT);
                break;
        }
    }

    private void consume(TokenType tokenType) {
        if (currentToken.getType().equals(tokenType)) {
            currentToken = lexer.getNextToken();
            return;
        }
        throw new IllegalStateException("Encountered unexpected token: " + currentToken.getType() + ", while expecting token with type: " + tokenType);
    }

    public Map<String, Double> bindings() {
        return bindings;
    }
}
