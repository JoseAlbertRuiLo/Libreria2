
package libreria;
    import java.util.ArrayList;

public class Administrator extends User {
    private ArrayList<Permissions> permissions;
    private boolean isSuperAdmin;

    public Administrator(Profile profile, String username, String password, ArrayList<Permissions> permissions, boolean isSuperAdmin) {
        super(profile, username, password);
        this.permissions = permissions;
        this.isSuperAdmin = isSuperAdmin;
    }

    public ArrayList<Permissions> getPermissions() {
        return permissions;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }


}
