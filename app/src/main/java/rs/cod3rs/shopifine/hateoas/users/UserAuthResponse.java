package rs.cod3rs.shopifine.hateoas.users;

public class UserAuthResponse {

    private String token;

    public UserAuthResponse() {
        super();
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
