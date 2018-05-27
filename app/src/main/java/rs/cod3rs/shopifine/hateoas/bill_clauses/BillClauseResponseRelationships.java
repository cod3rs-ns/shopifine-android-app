package rs.cod3rs.shopifine.hateoas.bill_clauses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.ResponseRelationship;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillClauseResponseRelationships {

    private ResponseRelationship product;

    public BillClauseResponseRelationships() {
        super();
    }

    public ResponseRelationship getProduct() {
        return product;
    }

    public void setProduct(ResponseRelationship product) {
        this.product = product;
    }
}
