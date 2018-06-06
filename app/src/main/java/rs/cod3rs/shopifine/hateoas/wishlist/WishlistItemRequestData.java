package rs.cod3rs.shopifine.hateoas.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishlistItemRequestData {

    private String type;

    private WishlistItemRequestAttributes attributes;

    private WishlistItemRequestRelationships relationships;

    public WishlistItemRequestData() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public WishlistItemRequestAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final WishlistItemRequestAttributes attributes) {
        this.attributes = attributes;
    }

    public WishlistItemRequestRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(final WishlistItemRequestRelationships relationships) {
        this.relationships = relationships;
    }
}
