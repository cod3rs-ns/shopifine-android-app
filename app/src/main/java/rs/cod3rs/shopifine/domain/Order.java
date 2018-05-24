package rs.cod3rs.shopifine.domain;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import rs.cod3rs.shopifine.Util;

public class Order {

    public Long id;
    public LocalDateTime createdAt;
    public String state;
    public Integer totalItems;
    public Double amount;
    public Double discount;
    public Integer pointsGained;
    public Integer pointsSpent;

    public Order(
            final Long id,
            final String createdAt,
            final String state,
            final Integer totalItems,
            final Double amount,
            final Double discount,
            final Integer pointsGained,
            final Integer pointsSpent) {
        this.id = id;
        this.createdAt =
                LocalDateTime.parse(createdAt, DateTimeFormat.forPattern(Util.DATE_PATTERN));
        this.state = state;
        this.totalItems = totalItems;
        this.amount = amount;
        this.discount = discount;
        this.pointsGained = pointsGained;
        this.pointsSpent = pointsSpent;
    }

    @Override
    public String toString() {
        return "Order{"
                + "id="
                + id
                + ", createdAt="
                + createdAt
                + ", state='"
                + state
                + '\''
                + ", totalItems="
                + totalItems
                + ", amount="
                + amount
                + ", discount="
                + discount
                + ", pointsGained="
                + pointsGained
                + ", pointsSpent="
                + pointsSpent
                + '}';
    }
}
