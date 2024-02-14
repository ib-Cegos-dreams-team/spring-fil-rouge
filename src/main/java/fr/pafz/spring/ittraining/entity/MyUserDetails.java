package fr.pafz.spring.ittraining.entity;

import fr.pafz.spring.ittraining.Enum.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUserDetails extends User {

    private Role role;
    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Role role) {
        super(username, password, authorities);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }


}
