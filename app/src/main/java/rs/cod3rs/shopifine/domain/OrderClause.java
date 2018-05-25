package rs.cod3rs.shopifine.domain;

public class OrderClause {

    public Integer id;
    public Integer ordinal;
    public Integer quantity;
    public Double price;
    public Double amount;
    public Double discount;
    public Double discountAmount;

    public OrderClause(
            final Integer id,
            final Integer ordinal,
            final Integer quantity,
            final Double price,
            final Double amount,
            final Double discount,
            final Double discountAmount) {
        this.id = id;
        this.ordinal = ordinal;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.discount = discount;
        this.discountAmount = discountAmount;
    }
}
