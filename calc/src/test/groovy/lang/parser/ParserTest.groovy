package lang.parser

import lang.lexer.Lexer
import lang.lexer.Token
import lang.lexer.TokenType
import lang.parser.ast.ASTNode
import spock.lang.Ignore
import spock.lang.Specification

class ParserTest extends Specification {

    Parser parser;

    void setup() {
        parser = new Parser(new Lexer())
    }

    def 'parse simple expression into AST'() {
        given:
        def expression = '1 + 2'
        parser.setExpression(expression)
        when:
        def ast = parser.parse();
        then:
        def expected = ASTNode.node(new Token(TokenType.PLUS, '+'), ASTNode.leaf(new Token(TokenType.NUMBER, 1.0d)), ASTNode.leaf(new Token(TokenType.NUMBER, 2.0d)))
        ast == expected;
    }

    // TODO: Tough! How to write equals and hashcode for a tree?
    @Ignore
    def 'parse expression with operator precedence into AST'() {
        given:
        def expression = '1 * 2 + 6'
        parser.setExpression(expression)
        when:
        def ast = parser.parse();
        then:
        def expected = ASTNode.node(new Token(TokenType.PLUS, '+'), ASTNode.node(new Token(TokenType.MULTIPLY, '*'), ASTNode.leaf(new Token(TokenType.NUMBER, 1.0d)), ASTNode.leaf(new Token(TokenType.NUMBER, 2.0d))), ASTNode.leaf(new Token(TokenType.NUMBER, 6.0)));
        ast == expected;
    }
}
