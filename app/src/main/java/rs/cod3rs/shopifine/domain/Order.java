package rs.cod3rs.shopifine.domain;

import java.util.Date;

public class Order {

    public Integer id;

    public Date createdAt;

    public Integer totalItems;

    public Order(Integer id, Date createdAt, Integer totalItems) {
        this.id = id;
        this.createdAt = createdAt;
        this.totalItems = totalItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", totalItems=" + totalItems +
                '}';
    }
}
