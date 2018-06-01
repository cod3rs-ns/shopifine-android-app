package rs.cod3rs.shopifine.hateoas.buyer_categories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyerCategoryAttributes {

    private String name;

    public BuyerCategoryAttributes() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
