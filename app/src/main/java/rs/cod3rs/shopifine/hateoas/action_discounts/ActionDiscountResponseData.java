package rs.cod3rs.shopifine.hateoas.action_discounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.OrderClause;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionDiscountResponseData {

    private String type;
    private Long id;
    private ActionDiscountAttributes attributes;

    public ActionDiscountResponseData() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ActionDiscountAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final ActionDiscountAttributes attributes) {
        this.attributes = attributes;
    }
}
