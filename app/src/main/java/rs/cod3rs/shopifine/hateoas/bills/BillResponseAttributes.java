package rs.cod3rs.shopifine.hateoas.bills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillResponseAttributes {
    private String createdAt;
    private String state;
    private Integer totalItems;
    private Double amount;
    private Double discount;
    private Integer pointsGained;
    private Integer pointsSpent;

    public BillResponseAttributes() {
        super();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getPointsGained() {
        return pointsGained;
    }

    public void setPointsGained(Integer pointsGained) {
        this.pointsGained = pointsGained;
    }

    public Integer getPointsSpent() {
        return pointsSpent;
    }

    public void setPointsSpent(Integer pointsSpent) {
        this.pointsSpent = pointsSpent;
    }
}
