package rs.cod3rs.shopifine.hateoas.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {

    private ProductResponseData data;

    public ProductResponse() {
        super();
    }

    public ProductResponseData getData() {
        return data;
    }

    public void setData(final ProductResponseData data) {
        this.data = data;
    }
}
