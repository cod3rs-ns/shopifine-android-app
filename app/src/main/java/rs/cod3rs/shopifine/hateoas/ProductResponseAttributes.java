package rs.cod3rs.shopifine.hateoas;

public class ProductResponseAttributes {

    private final String name;
    private final String imageUrl;
    private final Double price;

    public ProductResponseAttributes(final String name, final String imageUrl, final Double price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getPrice() {
        return price;
    }
}
