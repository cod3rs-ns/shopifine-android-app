package rs.cod3rs.shopifine.notification;

public enum NotificationType {

    ORDER_STATUS_CHANGED("OrderStatusChanged"),
    ACTION_DISCOUNT_CREATED("ActionDiscountCreated"),
    ORDER_ADDRESS_CHANGED("OrderAddressChanged"),
    PRODUCT_PRICE_CHANGED("ProductPriceChanged"),
    ONE_PRODUCT_LEFT("OneProductLeft");

    private final String name;

    NotificationType(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static NotificationType fromString(final String text) {
        for (final NotificationType nt : NotificationType.values()) {
            if (nt.name.equalsIgnoreCase(text)) {
                return nt;
            }
        }
        return null;
    }
}
