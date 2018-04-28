package rs.cod3rs.shopifine.hateoas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseData {

    private String type;
    private Long id;
    private ProductResponseAttributes attributes;

    public ProductResponseData() {
        super();
    }

    public Product toDomain() {
        return new Product(id, attributes.getName(), attributes.getImageUrl(), 1L, attributes.getPrice());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductResponseAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final ProductResponseAttributes attributes) {
        this.attributes = attributes;
    }
}
