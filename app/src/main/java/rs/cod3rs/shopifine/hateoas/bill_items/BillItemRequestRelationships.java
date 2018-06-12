package rs.cod3rs.shopifine.hateoas.bill_items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.RequestRelationship;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillItemRequestRelationships {

    private RequestRelationship product;

    private RequestRelationship bill;

    public BillItemRequestRelationships(final RequestRelationship product, final RequestRelationship bill) {
        this.product = product;
        this.bill = bill;
    }

    public BillItemRequestRelationships() {
        super();
    }

    public RequestRelationship getProduct() {
        return product;
    }

    public void setProduct(final RequestRelationship product) {
        this.product = product;
    }

    public RequestRelationship getBill() {
        return bill;
    }

    public void setBill(final RequestRelationship bill) {
        this.bill = bill;
    }
}
