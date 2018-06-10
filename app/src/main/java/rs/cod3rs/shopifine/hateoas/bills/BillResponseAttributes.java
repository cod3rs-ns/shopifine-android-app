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
    private String address;
    private Double longitude;
    private Double latitude;

    public BillResponseAttributes() {
        super();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(final Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(final Double discount) {
        this.discount = discount;
    }

    public Integer getPointsGained() {
        return pointsGained;
    }

    public void setPointsGained(final Integer pointsGained) {
        this.pointsGained = pointsGained;
    }

    public Integer getPointsSpent() {
        return pointsSpent;
    }

    public void setPointsSpent(final Integer pointsSpent) {
        this.pointsSpent = pointsSpent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }
}
