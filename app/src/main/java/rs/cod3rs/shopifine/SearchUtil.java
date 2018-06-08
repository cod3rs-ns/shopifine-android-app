package rs.cod3rs.shopifine;

public class SearchUtil {
    private static String priceFilter(final int priceFilterId) {
        switch (priceFilterId) {
            case R.id.filterLowestPrice:
                return "&filter[price-range-to]=100";
            case R.id.filterLowPrice:
                return "&filter[price-range-from]=100&filter[price-range-to]=1000";
            case R.id.filterMediumPrice:
                return "&filter[price-range-from]=1000&filter[price-range-to]=5000";
            case R.id.filterHighPrice:
                return "&filter[price-range-from]=5000";
            default:
                return null;
        }
    }

    private static String categoryFilter(final int categoryFilterId) {
        if (categoryFilterId != -1) {
            return String.format("&filter[category]=%s", String.valueOf(categoryFilterId));
        }

        return null;
    }

    public static String formatQuery(final Integer priceFilterId, final Integer categoryFilterId) {
        final String priceQuery = priceFilter(priceFilterId);
        final String categoryQuery = categoryFilter(categoryFilterId);

        if (priceQuery != null && categoryQuery != null) {
            return String.format("%s%s", priceQuery, categoryQuery);
        } else if (priceQuery != null) {
            return priceQuery;
        } else if (categoryQuery != null) {
            return categoryQuery;
        }

        return null;
    }
}
