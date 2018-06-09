package rs.cod3rs.shopifine.domain;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rs.cod3rs.shopifine.Util;

public class Order implements Serializable {

    public Long id;
    public LocalDateTime createdAt;
    public OrderState state;
    public Integer totalItems;
    public Double amount;
    public Double discount;
    public Integer pointsGained;
    public Integer pointsSpent;
    public List<Discount> discounts;
    public String address;
    public Double longitude;
    public Double latitude;

    public Order() {
    }

    public Order(
            final Long id,
            final String createdAt,
            final String state,
            final Integer totalItems,
            final Double amount,
            final Double discount,
            final Integer pointsGained,
            final Integer pointsSpent,
            final String address,
            final Double longitude,
            final Double latitude) {
        this.id = id;
        this.createdAt = LocalDateTime.parse(createdAt, DateTimeFormat.forPattern(Util.DATE_PATTERN));
        this.state = OrderState.valueOf(state);
        this.totalItems = totalItems;
        this.amount = amount;
        this.discount = discount;
        this.pointsGained = pointsGained;
        this.pointsSpent = pointsSpent;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
