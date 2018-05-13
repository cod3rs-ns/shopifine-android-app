package rs.cod3rs.shopifine.hateoas.buyer_categories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.users.UserResponseRelationships;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyerCategoryResponseData {

    private String type;
    private BuyerCategoryAttributes attributes;

    public BuyerCategoryResponseData() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public BuyerCategoryAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final BuyerCategoryAttributes attributes) {
        this.attributes = attributes;
    }

}
