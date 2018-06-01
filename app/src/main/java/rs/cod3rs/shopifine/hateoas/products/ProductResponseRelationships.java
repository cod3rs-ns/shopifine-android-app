package rs.cod3rs.shopifine.hateoas.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.ResponseRelationship;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseRelationships {

    private ResponseRelationship category;

    public ProductResponseRelationships() {
        super();
    }

    public ResponseRelationship getCategory() {
        return category;
    }

    public void setCategory(final ResponseRelationship category) {
        this.category = category;
    }
}
