package AndrewS.GraduationTJ.model;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

public class User extends AbstractNamedEntity {

    private String email;

    private String password;

    private boolean enabled = true;

    private Date registered = new Date();

    private Set<Role> roles;

    public User(int id, String name, String email, String password, boolean enabled, Set<Role> roles) {
        super(id,name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public User(int id, String name, String email, String password, boolean enabled, Role role, Role... roles) {
        this(id, name, email, password, enabled, EnumSet.of(role, roles));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
