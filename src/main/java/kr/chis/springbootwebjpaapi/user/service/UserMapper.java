package kr.chis.springbootwebjpaapi.user.service;

import kr.chis.springbootwebjpaapi.user.repository.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author InSeok
 * Date : 2020/12/04
 * Remark :
 */
@Getter
@Setter
@Builder
public class UserMapper {
    private Long id;

    private String email;
    private String name;
    private String cellPhone;
    private String password;
    private Boolean active;

    private final Set<String> authorities = new HashSet<>();

    public User convertUser(PasswordEncoder passwordEncoder){
        if (active == null) active = true;
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .cellPhone(cellPhone)
                .password(passwordEncoder.encode(password))
                .active(active).build();
    }

    public UserMapper addAuthority(String authority){
        this.authorities.add(authority);
        return this;
    }
    public UserMapper removeAuthority(String  authority){
        this.authorities.remove(authority);
        return this;
    }
}
