package rs.cod3rs.shopifine.hateoas.discounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountResponseAttributes {
    private String name;
    private Double discount;
    private String type;

    public DiscountResponseAttributes() {}

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(final Double discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
