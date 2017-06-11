package exchange.utils;

/**
 * Created by alex on 2017-06-11.
 */
public class PathUtils {
    private static String PATH_TO_ACCOUNTS = "D:\\accounts";
    public static String UTF_8 = "UTF-8";

    public static String getFilePath(final String accountNumber) {
        return String.format("%s\\%s.acc", PATH_TO_ACCOUNTS, accountNumber);
    }
}
