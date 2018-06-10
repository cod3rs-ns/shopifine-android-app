package rs.cod3rs.shopifine.hateoas.bills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.ResponseRelationship;
import rs.cod3rs.shopifine.hateoas.ResponseRelationshipCollection;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillResponseRelationships {

    private ResponseRelationship customer;

    private ResponseRelationshipCollection items;

    private ResponseRelationshipCollection discounts;

    public BillResponseRelationships(final ResponseRelationship customer, final ResponseRelationshipCollection items, final ResponseRelationshipCollection discounts) {
        this.customer = customer;
        this.items = items;
        this.discounts = discounts;
    }

    public BillResponseRelationships() {
        super();
    }

    public ResponseRelationship getCustomer() {
        return customer;
    }

    public void setCustomer(final ResponseRelationship customer) {
        this.customer = customer;
    }

    public ResponseRelationshipCollection getItems() {
        return items;
    }

    public void setItems(final ResponseRelationshipCollection items) {
        this.items = items;
    }

    public ResponseRelationshipCollection getDiscounts() {
        return discounts;
    }

    public void setDiscounts(final ResponseRelationshipCollection discounts) {
        this.discounts = discounts;
    }
}
