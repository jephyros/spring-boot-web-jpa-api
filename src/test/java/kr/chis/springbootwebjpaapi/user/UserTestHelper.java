package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestHelper {

    private PasswordEncoder passwordEncoder;
    private User user1;
    private User user2;


    public UserTestHelper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

        user1 = new User().builder()
                .id(1L)
                .email("use1@mail.com")
                .name("사용자1")
                .cellPhone("010-1111-1111")
                .password(passwordEncoder.encode("1111"))
                .build();

        user2 = new User().builder()
                .id(2L)
                .email("use2@mail.com")
                .name("사용자2")
                .cellPhone("010-2222-2222")
                .password(passwordEncoder.encode("2222"))
                .build();
    }





    public User createOneUser(){
        return user1;
    }

    public void assertOneUser(User user){
        assertThat(user.getId()).as("Expect :" + user1.getId()).isEqualTo(user1.getId());
        assertThat(user.getUsername()).as("Expect :" + user1.getUsername()).isEqualTo(user1.getUsername());
        assertThat(user.getCellPhone()).as("Expect :" + user1.getCellPhone()).isEqualTo(user1.getCellPhone());
    }
}
