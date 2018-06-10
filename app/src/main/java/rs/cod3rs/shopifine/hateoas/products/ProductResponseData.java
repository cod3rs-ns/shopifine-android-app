package rs.cod3rs.shopifine.hateoas.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseData {

    private String type;
    private Long id;
    private ProductResponseAttributes attributes;
    private ProductResponseRelationships relationships;

    public ProductResponseData() {
        super();
    }

    public Product toDomain() {
        final Long categoryId = relationships.getCategory().getData().getId();
        return new Product(id, attributes.getName(), attributes.getImageUrl(), categoryId, attributes.getPrice(), attributes.getIsInWishlist());
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ProductResponseAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final ProductResponseAttributes attributes) {
        this.attributes = attributes;
    }

    public ProductResponseRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(final ProductResponseRelationships relationships) {
        this.relationships = relationships;
    }
}
