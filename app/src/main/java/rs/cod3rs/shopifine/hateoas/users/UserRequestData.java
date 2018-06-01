package rs.cod3rs.shopifine.hateoas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestData {

    private String type;
    private UserRequestAttributes attributes;
    private UserRequestRelationships relationships;

    public UserRequestData() {
        super();
    }

    public UserRequestData(final String type, final UserRequestAttributes attributes, final UserRequestRelationships relationships) {
        this.type = type;
        this.attributes = attributes;
        this.relationships = relationships;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public UserRequestAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final UserRequestAttributes attributes) {
        this.attributes = attributes;
    }

    public UserRequestRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(final UserRequestRelationships relationships) {
        this.relationships = relationships;
    }
}
