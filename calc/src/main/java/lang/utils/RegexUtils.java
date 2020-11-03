package lang.utils;

public class RegexUtils {
    public static boolean matchNumber(String s) {
        return s.matches("[0-9]");
    }

    public static boolean matchIdentifier(String s) {
        return s.matches("[a-zA-Z]");
    }

    public static boolean matchOperator(String s) {
        return s.matches("[\\+\\-\\*\\/\\(\\)=]");
    }

    public static boolean matchDelimiter(String s) {
        return s.matches("[\\.]");
    }
}
