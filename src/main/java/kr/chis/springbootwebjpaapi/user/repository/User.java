package kr.chis.springbootwebjpaapi.user.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name="bs_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    private Long id;

    private String email;
    private String name;
    private String cellPhone;
    private String password;
    private Boolean active;

    //private Set<Authority> authorities;
    //todo -

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    @Override
    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return active;
    }

    public boolean isAccountNonLocked() {
        return active;
    }

    public boolean isCredentialsNonExpired() {
        return active;
    }

    public boolean isEnabled() {
        return active;
    }
}
