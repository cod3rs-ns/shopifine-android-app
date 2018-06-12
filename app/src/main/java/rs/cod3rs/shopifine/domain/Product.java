package rs.cod3rs.shopifine.domain;

import java.io.Serializable;

public class Product implements Serializable {
    public Long id;

    public String name;

    public String imageUrl;

    public Long categoryId;

    public Double price;

    public ProductCategory category;

    public Boolean isInWishlist = false;

    public Product() {
        super();
    }

    public Product(final Long id, final String name, final String imageUrl, final Long categoryId, final Double price, final Boolean isInWishlist) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.price = price;
        this.isInWishlist = isInWishlist;
    }
}
