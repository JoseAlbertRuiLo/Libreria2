
package libreria;


public class User {
    private Profile profile;
    private String username;
    private String password;

    public User(Profile profile, String username, String password) {
        this.profile = profile;
        this.username = username;
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
