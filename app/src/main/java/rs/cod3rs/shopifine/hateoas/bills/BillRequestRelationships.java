package rs.cod3rs.shopifine.hateoas.bills;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.RelationshipData;
import rs.cod3rs.shopifine.hateoas.RequestRelationship;

import static rs.cod3rs.shopifine.hateoas.DataTypes.USER_TYPE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillRequestRelationships {

    private RequestRelationship customer;

    public BillRequestRelationships() {
        super();
    }

    public BillRequestRelationships(final RequestRelationship customer) {
        this.customer = customer;
    }

    public BillRequestRelationships(final Integer userId) {
        this.customer = new RequestRelationship(new RelationshipData(USER_TYPE, userId.longValue()));
    }

    public RequestRelationship getCustomer() {
        return customer;
    }

    public void setCustomer(final RequestRelationship customer) {
        this.customer = customer;
    }
}
