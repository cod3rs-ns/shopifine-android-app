package rs.cod3rs.shopifine.hateoas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseData {

    private String type;
    private UserResponseAttributes attributes;
    private UserResponseRelationships relationships;

    public UserResponseData() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public UserResponseAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final UserResponseAttributes attributes) {
        this.attributes = attributes;
    }

    public UserResponseRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(UserResponseRelationships relationships) {
        this.relationships = relationships;
    }
}
