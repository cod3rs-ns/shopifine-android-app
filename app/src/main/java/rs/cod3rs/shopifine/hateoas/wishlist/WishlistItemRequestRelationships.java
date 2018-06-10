package rs.cod3rs.shopifine.hateoas.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.RequestRelationship;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishlistItemRequestRelationships {

    private RequestRelationship product;

    private RequestRelationship customer;

    public WishlistItemRequestRelationships() {
        super();
    }

    public WishlistItemRequestRelationships(RequestRelationship product, RequestRelationship customer) {
        this.product = product;
        this.customer = customer;
    }

    public RequestRelationship getProduct() {
        return product;
    }

    public void setProduct(final RequestRelationship product) {
        this.product = product;
    }

    public RequestRelationship getCustomer() {
        return customer;
    }

    public void setCustomer(final RequestRelationship customer) {
        this.customer = customer;
    }
}
