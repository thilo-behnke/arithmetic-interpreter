package server.dto;

import lang.parser.ast.ASTNode;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class InterpreterResultDTO {
    private final double result;
    private final ASTNode ast;
    private final Set<Map.Entry<String, Double>> bindings;
}
