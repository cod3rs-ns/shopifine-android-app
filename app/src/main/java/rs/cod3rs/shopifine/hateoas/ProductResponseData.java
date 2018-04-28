package rs.cod3rs.shopifine.hateoas;

import rs.cod3rs.shopifine.domain.Product;

public class ProductResponseData {

    private final String type;
    private final Long id;
    private final ProductResponseAttributes attributes;

    public ProductResponseData(final String type, final Long id, final ProductResponseAttributes attributes) {
        this.type = type;
        this.id = id;
        this.attributes = attributes;
    }

    public Product toDomain() {
        return new Product(id, attributes.getName(), attributes.getImageUrl(), 1L, attributes.getPrice());
    }

    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public ProductResponseAttributes getAttributes() {
        return attributes;
    }
}
