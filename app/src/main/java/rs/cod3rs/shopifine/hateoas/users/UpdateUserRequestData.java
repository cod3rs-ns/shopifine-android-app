package rs.cod3rs.shopifine.hateoas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserRequestData {

    private String type;
    private UpdateUserRequestAttributes attributes;

    public UpdateUserRequestData() {
        super();
    }

    public UpdateUserRequestData(final String type, final UpdateUserRequestAttributes attributes) {
        this.type = type;
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public UpdateUserRequestAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(final UpdateUserRequestAttributes attributes) {
        this.attributes = attributes;
    }
}
