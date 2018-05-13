package rs.cod3rs.shopifine.domain;

import java.io.Serializable;

public class ProductCategory implements Serializable {

    public Long id;

    public String name;

    public Double maxDiscount;

    public Boolean isConsumerGoods;

    public ProductCategory() {
    }

    public ProductCategory(Long id, String name, Double maxDiscount, Boolean isConsumerGoods) {
        this.id = id;
        this.name = name;
        this.maxDiscount = maxDiscount;
        this.isConsumerGoods = isConsumerGoods;
    }
}
