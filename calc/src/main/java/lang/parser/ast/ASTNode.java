package lang.parser.ast;

import lang.lexer.Token;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ASTNode {
    // TODO: Rename getValue into getToken!
    private final Token<Object> value;
    private final List<ASTNode> children;

    private ASTNode(Token<Object> value, List<ASTNode> children) {
        this.value = value;
        this.children = children;
    }

    public static ASTNode leaf(Token<Object> value) {
        return new ASTNode(value, new ArrayList<>());
    }

    public static ASTNode node(Token<Object> value, ASTNode ...children) {
        List<ASTNode> nodes = new ArrayList<>(Arrays.asList(children));
        return new ASTNode(value, nodes);
    }

    public Token<Object> getValue() {
        return value;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return children.size() <= 0;
    }

    public boolean isNode() {
        return children.size() > 0;
    }
}
