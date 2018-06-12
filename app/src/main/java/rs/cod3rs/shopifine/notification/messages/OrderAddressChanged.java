package rs.cod3rs.shopifine.notification.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.joda.time.DateTime;

import java.io.Serializable;

import rs.cod3rs.shopifine.domain.Order;
import rs.cod3rs.shopifine.domain.OrderState;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderAddressChanged implements Serializable {

    private Long orderId;
    private OrderState state;
    private String address;
    private Double latitude;
    private Double longitude;
    private String createdAt;
    private Integer totalItems;
    private Double amount;
    private Double discount;
    private Integer pointsGained;
    private Integer pointsSpent;

    public OrderAddressChanged() {
        super();
    }

    public Order toDomain() {
        return new Order(this.orderId, this.createdAt, this.state.toString(),
                this.totalItems, this.amount, this.discount, this.pointsGained,
                this.pointsSpent, this.address, this.longitude, this.latitude);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
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

    public OrderState getState() {
        return state;
    }

    public void setState(final OrderState state) {
        this.state = state;
    }
}
