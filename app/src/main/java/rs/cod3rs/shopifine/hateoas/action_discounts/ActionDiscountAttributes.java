package rs.cod3rs.shopifine.hateoas.action_discounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionDiscountAttributes {

    private String name;
    private String from;
    private String to;
    private Double discount;

    public ActionDiscountAttributes() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(final String to) {
        this.to = to;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(final Double discount) {
        this.discount = discount;
    }
}
