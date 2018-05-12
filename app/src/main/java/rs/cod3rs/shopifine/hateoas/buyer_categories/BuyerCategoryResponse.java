package rs.cod3rs.shopifine.hateoas.buyer_categories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyerCategoryResponse {

    private BuyerCategoryResponseData data;

    public BuyerCategoryResponse() {
        super();
    }

    public BuyerCategoryResponseData getData() {
        return data;
    }

    public void setData(final BuyerCategoryResponseData data) {
        this.data = data;
    }
}
