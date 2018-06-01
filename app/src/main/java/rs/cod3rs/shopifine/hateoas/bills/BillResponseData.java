package rs.cod3rs.shopifine.hateoas.bills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.Order;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillResponseData {

    private String type;
    private Long id;
    private BillResponseAttributes attributes;

    public BillResponseData() {
        super();
    }

    public Order toDomain() {
        return new Order(
                id,
                attributes.getCreatedAt(),
                attributes.getState(),
                attributes.getTotalItems(),
                attributes.getAmount(),
                attributes.getDiscount(),
                attributes.getPointsGained(),
                attributes.getPointsSpent());
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BillResponseAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final BillResponseAttributes attributes) {
        this.attributes = attributes;
    }
}
