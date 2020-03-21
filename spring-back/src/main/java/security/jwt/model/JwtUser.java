package security.jwt.model;

import model.entity.Role;
import model.entity.User;
import model.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class JwtUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
    private final boolean active;
    private final boolean isEnabled;
    private final Date lastPasswordResetDate;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public JwtUser(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getFirstName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.active = user.getStatus().equals(AccountStatus.ACTIVE);
        this.authorities = rolesToAuthorities(user.getRoles());
        this.lastPasswordResetDate = user.getUpdatedTimestamp();
        this.isEnabled = user.isEnabled();
    }

    private List<GrantedAuthority> rolesToAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
