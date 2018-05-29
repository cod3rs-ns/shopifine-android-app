package rs.cod3rs.shopifine.domain;

public class ProductCategory {

    public Long id;

    public String name;

    public Double maxDiscount;

    public Boolean isConsumerGoods;

    public ProductCategory() {
    }

    public ProductCategory(final Long id, final String name, final Double maxDiscount, final Boolean isConsumerGoods) {
        this.id = id;
        this.name = name;
        this.maxDiscount = maxDiscount;
        this.isConsumerGoods = isConsumerGoods;
    }
}
