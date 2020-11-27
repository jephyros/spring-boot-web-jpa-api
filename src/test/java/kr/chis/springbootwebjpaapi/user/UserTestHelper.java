package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestHelper {

    private PasswordEncoder passwordEncoder;
    private User user1;
    private User user2;


    public UserTestHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        user1 = User.builder()
                .email("user1@mail.com")
                .name("사용자1")
                .cellPhone("010-1111-1111")
                .active(true)
                .password(passwordEncoder.encode("1111"))
                .build();

        user2 = User.builder()
                .email("user2@mail.com")
                .name("사용자2")
                .cellPhone("010-2222-2222")
                .active(true)
                .password(passwordEncoder.encode("2222"))
                .build();
    }



    public User createUser1(){
        return user1;
    }

    public User createUser2(){
        return user2;
    }


    public void assertUser1(User user){
        assertThat(user.getId()).as("Expect :" + user1.getId()).isEqualTo(user1.getId());
        assertThat(user.getUsername()).as("Expect :" + user1.getUsername()).isEqualTo(user1.getUsername());
        assertThat(user.getCellPhone()).as("Expect :" + user1.getCellPhone()).isEqualTo(user1.getCellPhone());
        assertThat(user.getAuthorities().size()).as("Expect : X > 0").isGreaterThan(0);
    }

    public User createAdminRoleUser(){
        return user1.addAuthority(new Authority(Authority.ROLE_ADMIN));
    }

    public User createUserRoleUser(){
        return user1.addAuthority(new Authority(Authority.ROLE_USER));
    }
}
