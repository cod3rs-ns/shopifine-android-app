package rs.cod3rs.shopifine.hateoas.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseAttributes {

    private String name;
    private String imageUrl;
    private Double price;
    private Boolean isInWishlist;

    public ProductResponseAttributes() {
        super();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Boolean getIsInWishlist() {
        return isInWishlist;
    }

    public void setIsInWishlist(final Boolean inWishlist) {
        isInWishlist = inWishlist;
    }

}
