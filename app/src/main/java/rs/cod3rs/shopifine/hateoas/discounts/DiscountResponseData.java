package rs.cod3rs.shopifine.hateoas.discounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.domain.Discount;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountResponseData {

    private String type;
    private Long id;
    private DiscountResponseAttributes attributes;

    public DiscountResponseData() {
        super();
    }

    public Discount toDomain() {
        return new Discount(
                id, attributes.getName(), attributes.getType(), attributes.getDiscount());
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

    public DiscountResponseAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final DiscountResponseAttributes attributes) {
        this.attributes = attributes;
    }
}
