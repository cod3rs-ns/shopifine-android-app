package rs.cod3rs.shopifine.hateoas.bill_items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemRequestAttributes {

    private Double price;

    private Integer quantity;

    private Double discount;

    private Integer ordinal;

    public BillItemRequestAttributes(final Double price, final Integer quantity, final Double discount, final Integer ordinal) {
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.ordinal = ordinal;
    }

    public BillItemRequestAttributes() {
        super();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(final Double discount) {
        this.discount = discount;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(final Integer ordinal) {
        this.ordinal = ordinal;
    }
}
