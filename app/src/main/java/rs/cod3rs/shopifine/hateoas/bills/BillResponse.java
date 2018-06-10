package rs.cod3rs.shopifine.hateoas.bills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillResponse {

    private BillResponseData data;

    public BillResponse(final BillResponseData data) {
        this.data = data;
    }

    public BillResponse() {
        super();
    }

    public BillResponseData getData() {
        return data;
    }

    public void setData(final BillResponseData data) {
        this.data = data;
    }
}
