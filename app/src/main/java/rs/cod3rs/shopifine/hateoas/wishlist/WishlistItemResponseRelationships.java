package rs.cod3rs.shopifine.hateoas.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.ResponseRelationship;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishlistItemResponseRelationships {

    private ResponseRelationship product;

    private ResponseRelationship customer;

    public WishlistItemResponseRelationships() {
        super();
    }

    public ResponseRelationship getProduct() {
        return product;
    }

    public void setProduct(final ResponseRelationship product) {
        this.product = product;
    }

    public ResponseRelationship getCustomer() {
        return customer;
    }

    public void setCustomer(final ResponseRelationship customer) {
        this.customer = customer;
    }
}
