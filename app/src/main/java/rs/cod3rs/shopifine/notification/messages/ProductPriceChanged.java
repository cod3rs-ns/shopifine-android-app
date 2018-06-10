package rs.cod3rs.shopifine.notification.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import rs.cod3rs.shopifine.domain.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPriceChanged implements Serializable {

    private Long productId;
    private String name;
    private String imageUrl;
    private Long categoryId;
    private Double price;
    private Long quantity;

    public ProductPriceChanged() {
    }

    public Product fromDomain() {
        return new Product(productId, name, imageUrl, categoryId, price, false);
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }
}
