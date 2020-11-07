package kr.chis.springbootwebjpaapi.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @DisplayName("1. 유저를 저장한다.")
    @Test
    public void test_1(){
        userRepository.findAll();

    }
}
