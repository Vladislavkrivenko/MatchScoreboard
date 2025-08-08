package util;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static boolean isValidPlayerName(String name) {
        if (name == null) return false;
        name = name.trim();
        return name.length() >= 2 &&
                name.length() <= 20 &&
                name.matches("^[A-Za-z\\p{IsCyrillic}\\s]+$");
    }

}
