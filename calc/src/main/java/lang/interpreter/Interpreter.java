package lang.interpreter;

import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import lang.exception.NoBindingForVariableException;
import lang.handler.MathOperationHandler;
import lang.lexer.Token;
import lang.lexer.TokenType;
import lang.parser.Parser;
import lang.parser.ast.ASTNode;

import java.util.LinkedHashMap;
import java.util.Map;

public class Interpreter {

    private final Parser parser;
    private final Map<String, Double> bindings;
    private ASTNode ast;

    public Interpreter(Parser parser, @Nullable Map<String, Double> bindings) {
        this.parser = parser;
        this.bindings = bindings != null ? bindings : new LinkedHashMap<>();
    }

    public void setBindings(Map<String, Double> bindings) {
        this.bindings.clear();
        this.bindings.putAll(bindings);
    }

    public void setExpression(String expression) {
        parser.setExpression(expression);
    }

    public double eval() {
        ast = parser.parse();
        return walkTree(ast);
    }

    public double walkTree(ASTNode ast) {
        if (ast.isLeaf()) {
            if (ast.getValue().getType().equals(TokenType.IDENTIFIER)) {
                return getBindingValue(ast.getValue());
            } else if (ast.getValue().getType().equals(TokenType.NUMBER)) {
                return (double) ast.getValue().getValue();
            }
        }

        TokenType nodeType = ast.getValue().getType();
        // TODO: Handle error cases.
        if (nodeType.isFunction()) {
            ASTNode child = ast.getChildren().get(0);
            double childResult = walkTree(child);
            return MathOperationHandler.handleFunction(nodeType, childResult);
        } else if (nodeType.isArithmeticOperator() && ast.getChildren().size() == 2) {
            ASTNode childLeft = ast.getChildren().get(0);
            ASTNode childRight = ast.getChildren().get(1);
            double childLeftResult = walkTree(childLeft);
            double childRightResult = walkTree(childRight);
            return MathOperationHandler.handleOperation(childLeftResult, ast.getValue(), childRightResult);
        } else if (nodeType.equals(TokenType.MINUS) && ast.getChildren().size() == 1) {
            ASTNode child = ast.getChildren().get(0);
            double childResult = walkTree(child);
            return -1 * childResult;
        } else if (nodeType.isAssignment()) {
            ASTNode childLeft = ast.getChildren().get(0);
            ASTNode childRight = ast.getChildren().get(1);
            double childRightResult = walkTree(childRight);
            bindings().put((String) childLeft.getValue().getValue(), childRightResult);
            return childRightResult;
        }

        throw new UnsupportedOperationException();
    }

    private double getBindingValue(Token<Object> token) {
        Double bindingsValue = bindings().get((String) token.getValue());
        if (bindingsValue == null) {
            throw new NoBindingForVariableException(token.getValue().toString());
        }
        return bindingsValue;
    }

    public Map<String, Double> bindings() {
        return bindings;
    }

    public ASTNode getAst() {
        return ast;
    }
}
