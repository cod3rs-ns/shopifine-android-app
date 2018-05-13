package rs.cod3rs.shopifine.hateoas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.RequestRelationship;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestRelationships {

    private RequestRelationship buyerCategory;

    public UserRequestRelationships() {
        super();
    }

    public UserRequestRelationships(final RequestRelationship buyerCategory) {
        this.buyerCategory = buyerCategory;
    }

    public RequestRelationship getBuyerCategory() {
        return buyerCategory;
    }

    public void setBuyerCategory(final RequestRelationship buyerCategory) {
        this.buyerCategory = buyerCategory;
    }
}
