package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.service.UserMapper;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestHelper {

    private PasswordEncoder passwordEncoder;


    public UserTestHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username){
        return User.builder()
                .email(username +"@mail.com")
                .name(username +"이름")
                .cellPhone(username + "010-1111-1111")
                .active(true)
                .password(passwordEncoder.encode(username+"1234"))
                .build();
    }

    public UserMapper createUserMapper(String username){
        return UserMapper.builder()
                .email(username +"@mail.com")
                .name(username +"이름")
                .cellPhone(username + "010-1111-1111")
                .active(true)
                .password(username+"1234")
                .build();
    }

    public void assertUser(String username,User user){
        assertThat(user.getId()).as("아이디가 Null 아이니다 ").isNotNull();
        assertThat(user.getName()).as("Expect : " + username + "이름").isEqualTo(username + "이름");
        assertThat(user.getCellPhone()).as("Expect : " + username + "010-1111-1111").isEqualTo(username + "010-1111-1111");
    }


    public User createAdminRoleUser(String username){
        User user = createUser(username);
        return user.addAuthority(new Authority(Authority.ROLE_ADMIN));
    }

    public User createUserRoleUser(String username){
        User user = createUser(username);
        return user.addAuthority(new Authority(Authority.ROLE_USER));
    }
}
