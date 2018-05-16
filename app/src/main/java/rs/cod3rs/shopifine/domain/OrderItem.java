package rs.cod3rs.shopifine.domain;

public class OrderItem {

    public Integer id;

    public Integer ordinal;

    public Integer quantity;

    public Double price;

    public Double amount;

    public Double discount;

    public Double discountAmount;

    public OrderItem(Integer id, Integer ordinal, Integer quantity, Double price, Double amount, Double discount, Double discountAmount) {
        this.id = id;
        this.ordinal = ordinal;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.discount = discount;
        this.discountAmount = discountAmount;
    }
}