package kr.chis.springbootwebjpaapi.user.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.chis.springbootwebjpaapi.common.BaseEntity;
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
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String cellPhone;
    @JsonIgnore
    private String password;
    private Boolean active;

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    @JoinColumn(name ="user_id")
    private final Set<Authority> authorities = new HashSet<>();

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
        authority.authorityId.setUser_id(this.id);

        //동일 권한이 없을때만 추가한다.
        if (!authorities.contains(authority)) this.authorities.add(authority);
        return this;
    }
    public User removeAuthority(Authority authority){
        authority.authorityId.setUser_id(this.id);
        this.authorities.remove(authority);
        return this;
    }
}
