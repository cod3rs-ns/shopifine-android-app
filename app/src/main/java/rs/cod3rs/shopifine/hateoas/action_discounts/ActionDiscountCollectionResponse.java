package rs.cod3rs.shopifine.hateoas.action_discounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionDiscountCollectionResponse {

    private List<ActionDiscountResponseData> data;

    public ActionDiscountCollectionResponse() {
        super();
    }

    public List<ActionDiscountResponseData> getData() {
        return data;
    }

    public void setData(final List<ActionDiscountResponseData> data) {
        this.data = data;
    }

}
