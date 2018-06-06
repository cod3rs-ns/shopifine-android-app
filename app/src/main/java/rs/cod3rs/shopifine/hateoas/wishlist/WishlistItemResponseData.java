package rs.cod3rs.shopifine.hateoas.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.WishlistItem;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WishlistItemResponseData {

    private String type;

    private Long id;

    private WishlistItemResponseAttributes attributes;

    private WishlistItemResponseRelationships relationships;

    public WishlistItemResponseData() {
        super();
    }

    public WishlistItem toDomain() {
        return new WishlistItem(
                id, attributes.getCreatedAt(), relationships.getProduct().getData().getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public WishlistItemResponseAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final WishlistItemResponseAttributes attributes) {
        this.attributes = attributes;
    }

    public WishlistItemResponseRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(final WishlistItemResponseRelationships relationships) {
        this.relationships = relationships;
    }
}
