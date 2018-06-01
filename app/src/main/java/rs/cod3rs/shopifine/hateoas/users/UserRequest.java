package rs.cod3rs.shopifine.hateoas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    private UserRequestData data;

    public UserRequest() {
        super();
    }

    public UserRequest(UserRequestData data) {
        this.data = data;
    }

    public UserRequestData getData() {
        return data;
    }

    public void setData(final UserRequestData data) {
        this.data = data;
    }
}
