package rs.cod3rs.shopifine.domain;

import java.io.Serializable;

public class Discount implements Serializable {
    public Long id;
    public String name;
    public String type;
    public Double discount;

    public Discount() {
        super();
    }

    public Discount(final Long id, final String name, final String type, final Double discount) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.discount = discount;
    }
}
