package rs.cod3rs.shopifine.hateoas.bills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillRequestAttributes {

    private String state;

    private Long totalItems;

    private Long pointsSpent;

    public BillRequestAttributes() {
        super();
    }

    public BillRequestAttributes(final String state, final Long totalItems, final Long pointsSpent) {
        this.state = state;
        this.totalItems = totalItems;
        this.pointsSpent = pointsSpent;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(final Long totalItems) {
        this.totalItems = totalItems;
    }

    public Long getPointsSpent() {
        return pointsSpent;
    }

    public void setPointsSpent(final Long pointsSpent) {
        this.pointsSpent = pointsSpent;
    }
}
