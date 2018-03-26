package rs.cod3rs.shopifine.hateoas;

public class UserAuthRequest {

    private final String username;
    private final String password;

    public UserAuthRequest(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
