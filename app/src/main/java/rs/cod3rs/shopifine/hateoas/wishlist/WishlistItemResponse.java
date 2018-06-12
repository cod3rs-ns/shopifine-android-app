package rs.cod3rs.shopifine.hateoas.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishlistItemResponse {

    private WishlistItemResponseData data;

    public WishlistItemResponse() {
        super();
    }

    public WishlistItemResponseData getData() {
        return data;
    }

    public void setData(final WishlistItemResponseData data) {
        this.data = data;
    }
}
