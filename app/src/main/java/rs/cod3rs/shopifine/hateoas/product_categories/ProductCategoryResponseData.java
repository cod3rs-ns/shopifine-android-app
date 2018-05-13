package rs.cod3rs.shopifine.hateoas.product_categories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.ProductCategory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCategoryResponseData {

    private Long id;
    private String type;
    private ProductCategoryResponseAttributes attributes;

    public ProductCategoryResponseData() {
        super();
    }

    public ProductCategory toDomain() {
        return new ProductCategory(id, attributes.getName(), attributes.getMaxDiscount(), attributes.isConsumerGoods);
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

    public ProductCategoryResponseAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final ProductCategoryResponseAttributes attributes) {
        this.attributes = attributes;
    }

}
