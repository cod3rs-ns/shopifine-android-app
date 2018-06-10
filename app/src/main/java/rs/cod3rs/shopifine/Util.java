package rs.cod3rs.shopifine;

import java.util.Locale;

public class Util {
    public static String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static String formatPrice(final Double price) {
        return String.format(Locale.getDefault(), "%.2f €", price);
    }

    public static boolean nonEmpty(final String value) {
        return !value.isEmpty();
    }
}
