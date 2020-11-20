package kr.chis.springbootwebjpaapi.user.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="bs_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String cellPhone;
    private String password;
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name ="user_id")
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public User addAuthority(Authority authority){
        //todo 권한추가 시 set 을 사용할 경우 복합키?
        this.authorities.add(authority);
        return this;
    }
    public User removeAuthority(Authority authority){
        this.authorities.remove(authority);
        return this;
    }
}
