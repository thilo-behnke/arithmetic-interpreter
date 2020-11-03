package lang.exception;

public class NoBindingForVariableException extends RuntimeException {
    public NoBindingForVariableException(String message) {
        super("No binding found for variable: " + message);
    }
}
