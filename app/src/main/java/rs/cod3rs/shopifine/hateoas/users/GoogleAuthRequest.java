package rs.cod3rs.shopifine.hateoas.users;

public class GoogleAuthRequest {

    private final String idToken;

    public GoogleAuthRequest(final String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }
}
