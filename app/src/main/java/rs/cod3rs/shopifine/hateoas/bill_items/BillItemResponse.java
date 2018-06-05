package rs.cod3rs.shopifine.hateoas.bill_items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemResponse {

    private BillItemResponseData data;

    public BillItemResponse(final BillItemResponseData data) {
        this.data = data;
    }

    public BillItemResponse() {
        super();
    }

    public BillItemResponseData getData() {
        return data;
    }

    public void setData(final BillItemResponseData data) {
        this.data = data;
    }
}
