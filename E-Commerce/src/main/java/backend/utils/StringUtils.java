package backend.utils;

import java.util.Arrays;
import java.util.List;

public class StringUtils {
    public static List<String> splitByNewline(String text) {
        return Arrays.asList(text.split("\\n"));
    }
}
