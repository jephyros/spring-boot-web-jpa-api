package kr.chis.springbootwebjpaapi.user.repository;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author InSeok
 * Date : 2020/11/21
 * Remark :
 */

@Data
@Embeddable
public class AuthorityId implements Serializable {

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "authority_role")
    private String authority;


}
