package rs.cod3rs.shopifine.domain;

public class User {
    public String username;

    public String firstName;

    public String lastName;

    public String address;

    public User(final String username, final String firstName, final String lastName, final String address) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public String getImage() {
        return String.format("%s%s", AVATARS_BASE_URL, this.username);
    }

    private static final String AVATARS_BASE_URL = "https://avatars.io/instagram/";
}
