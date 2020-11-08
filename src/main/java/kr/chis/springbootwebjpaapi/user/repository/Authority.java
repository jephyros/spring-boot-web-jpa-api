package kr.chis.springbootwebjpaapi.user.repository;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Authority implements GrantedAuthority {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final Authority USER = new Authority(ROLE_USER);
    public static final Authority ADMIN = new Authority(ROLE_ADMIN);


    private String authority;

    public String getAuthority() {
        return authority;
    }
}
