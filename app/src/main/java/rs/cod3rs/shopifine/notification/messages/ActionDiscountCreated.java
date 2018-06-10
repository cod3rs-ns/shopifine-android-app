package rs.cod3rs.shopifine.notification.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionDiscountCreated implements Serializable {

    private Long discountId;
    private String name;
    private String from;
    private String to;
    private Double discount;

    public ActionDiscountCreated() {
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(final Long discountId) {
        this.discountId = discountId;
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
