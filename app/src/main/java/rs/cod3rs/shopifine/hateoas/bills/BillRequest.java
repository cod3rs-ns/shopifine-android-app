package rs.cod3rs.shopifine.hateoas.bills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.OrderState;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillRequest {

    private BillRequestData data;

    public BillRequest(final BillRequestData data) {
        this.data = data;
    }

    public BillRequest() {
        super();
    }

    public BillRequestData getData() {
        return data;
    }

    public void setData(final BillRequestData data) {
        this.data = data;
    }

    public static BillRequest order(final Integer userId, final Long totalItems, final Long pointsSpent) {
        final BillRequestAttributes attributes = new BillRequestAttributes(OrderState.ORDERED.name(), totalItems, pointsSpent);
        final BillRequestRelationships relationships = new BillRequestRelationships(userId);
        final BillRequestData data = new BillRequestData(attributes, relationships);

        return new BillRequest(data);
    }
}
