package rs.cod3rs.shopifine.hateoas.bill_items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.ResponseRelationship;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemResponseRelationships {

    private ResponseRelationship product;

    public BillItemResponseRelationships() {
        super();
    }

    public ResponseRelationship getProduct() {
        return product;
    }

    public void setProduct(ResponseRelationship product) {
        this.product = product;
    }
}
