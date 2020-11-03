package lang.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static List<String> explode(String s) {
        if(s.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(s.split(""));
    }
}
