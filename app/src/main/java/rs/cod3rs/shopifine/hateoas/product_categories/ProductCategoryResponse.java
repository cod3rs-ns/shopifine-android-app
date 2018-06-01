package rs.cod3rs.shopifine.hateoas.product_categories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCategoryResponse {

    private ProductCategoryResponseData data;

    public ProductCategoryResponse() {
        super();
    }

    public ProductCategoryResponseData getData() {
        return data;
    }

    public void setData(final ProductCategoryResponseData data) {
        this.data = data;
    }
}
