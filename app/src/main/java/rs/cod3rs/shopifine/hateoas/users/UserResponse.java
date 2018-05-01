package rs.cod3rs.shopifine.hateoas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    private UserResponseData data;

    public UserResponse() {
        super();
    }

    public UserResponseData getData() {
        return data;
    }

    public void setData(final UserResponseData data) {
        this.data = data;
    }
}
