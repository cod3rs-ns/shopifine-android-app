package rs.cod3rs.shopifine.hateoas.product_categories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCategoryResponseAttributes {

    private String name;
    private Double maxDiscount;
    public Boolean isConsumerGoods;

    public ProductCategoryResponseAttributes() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(final Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }
}
