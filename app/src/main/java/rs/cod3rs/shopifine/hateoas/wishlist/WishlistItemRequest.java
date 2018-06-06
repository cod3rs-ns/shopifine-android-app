package rs.cod3rs.shopifine.hateoas.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishlistItemRequest {

    private WishlistItemRequestData data;

    public WishlistItemRequest() {
        super();
    }

    public WishlistItemRequestData getData() {
        return data;
    }

    public void setData(final WishlistItemRequestData data) {
        this.data = data;
    }
}
