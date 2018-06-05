package rs.cod3rs.shopifine.hateoas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserRequest {

    private UpdateUserRequestData data;

    public UpdateUserRequest() {
        super();
    }

    public UpdateUserRequest(UpdateUserRequestData data) {
        this.data = data;
    }

    public UpdateUserRequestData getData() {
        return data;
    }

    public void setData(final UpdateUserRequestData data) {
        this.data = data;
    }
}
