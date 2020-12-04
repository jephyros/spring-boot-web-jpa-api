package kr.chis.springbootwebjpaapi.user.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Set;

/**
 * @author InSeok
 * Date : 2020/12/04
 * Remark :
 */
@Getter
public class UserMapper {
    private Long id;

    private String email;
    private String name;
    private String cellPhone;
    private String password;
    private Boolean active;

    private Set<String> authorities;
}
