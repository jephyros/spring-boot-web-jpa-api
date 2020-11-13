package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.user.repository.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestHelper {

    private PasswordEncoder passwordEncoder;
    private User user1;
    private User user2;


    public UserTestHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        user1 = User.builder()
                .id(1L)
                .email("use1@mail.com")
                .name("사용자1")
                .cellPhone("010-1111-1111")
                .authorities(new HashSet<>())
                //.authorities(Sets.newSet(Authority.ADMIN))
                .password(passwordEncoder.encode("1111"))
                .build();

        new User();
        user1 = User.builder()
                .id(2L)
                .email("use2@mail.com")
                .name("사용자2")
                .cellPhone("010-2222-2222")
                .authorities(new HashSet<>())
                //.authorities(Sets.newSet(Authority.USER))
                .password(passwordEncoder.encode("2222"))
                .build();
    }





    public User createUser1(){
        return user1;
    }


    public void assertUser1(User user){
        assertThat(user.getId()).as("Expect :" + user1.getId()).isEqualTo(user1.getId());
        assertThat(user.getUsername()).as("Expect :" + user1.getUsername()).isEqualTo(user1.getUsername());
        assertThat(user.getCellPhone()).as("Expect :" + user1.getCellPhone()).isEqualTo(user1.getCellPhone());
    }
}
