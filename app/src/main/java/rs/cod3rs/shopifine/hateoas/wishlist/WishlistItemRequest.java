package rs.cod3rs.shopifine.hateoas.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.RelationshipData;
import rs.cod3rs.shopifine.hateoas.RequestRelationship;

import static rs.cod3rs.shopifine.hateoas.DataTypes.PRODUCTS_TYPE;
import static rs.cod3rs.shopifine.hateoas.DataTypes.USER_TYPE;
import static rs.cod3rs.shopifine.hateoas.DataTypes.WISHLIST_ITEM_TYPE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishlistItemRequest {

    private WishlistItemRequestData data;

    public WishlistItemRequest() {
        super();
    }

    public WishlistItemRequest(final WishlistItemRequestData data) {
        this.data = data;
    }

    public static WishlistItemRequest buildRequest(final Integer userId, final Long productId) {
        return new WishlistItemRequest(
                new WishlistItemRequestData(
                        WISHLIST_ITEM_TYPE,
                        new WishlistItemRequestAttributes(),
                        new WishlistItemRequestRelationships(
                                new RequestRelationship(
                                        new RelationshipData(PRODUCTS_TYPE, productId)),
                                new RequestRelationship(
                                        new RelationshipData(USER_TYPE, Long.valueOf(userId))))));
    }

    public WishlistItemRequestData getData() {
        return data;
    }

    public void setData(final WishlistItemRequestData data) {
        this.data = data;
    }
}
