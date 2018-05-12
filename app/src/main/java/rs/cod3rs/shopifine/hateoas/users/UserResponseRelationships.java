package rs.cod3rs.shopifine.hateoas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import rs.cod3rs.shopifine.hateoas.ResponseRelationship;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseRelationships {

    private ResponseRelationship buyerCategory;

    public UserResponseRelationships() {
        super();
    }

    public ResponseRelationship getBuyerCategory() {
        return buyerCategory;
    }

    public void setBuyerCategory(ResponseRelationship buyerCategory) {
        this.buyerCategory = buyerCategory;
    }
}
