package lang.lexer;

import lombok.Data;

@Data
public class Token<T> {
    private final TokenType type;
    private final T value;
}
