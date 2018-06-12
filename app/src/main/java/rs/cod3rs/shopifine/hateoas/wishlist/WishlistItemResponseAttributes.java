package rs.cod3rs.shopifine.hateoas.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishlistItemResponseAttributes {

    private String createdAt;

    public WishlistItemResponseAttributes() {
        super();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }
}
