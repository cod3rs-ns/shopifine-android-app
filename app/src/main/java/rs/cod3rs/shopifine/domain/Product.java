package rs.cod3rs.shopifine.domain;

public class Product {
    public Long id;

    public String name;

    public String imageUrl;

    public Long categoryId;

    public Double price;

    public Product(final Long id, final String name, final String imageUrl, final Long categoryId, final Double price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.price = price;
    }
}
