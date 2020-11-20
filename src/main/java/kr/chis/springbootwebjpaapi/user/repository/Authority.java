package kr.chis.springbootwebjpaapi.user.repository;

import kr.chis.springbootwebjpaapi.common.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="bs_authority")
public class Authority extends BaseEntity implements GrantedAuthority {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    //복합키를 사용한다.(user_id,authority_role)
    @EmbeddedId
    AuthorityId authorityId;


    public Authority(String authority) {
        this.authorityId = new AuthorityId();
        this.authorityId.setAuthority(authority);
    }

    public String getAuthority() {
        return authorityId.getAuthority();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return Objects.equals(authorityId, authority.authorityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorityId);
    }
}
