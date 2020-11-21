package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.repository.UserRepository;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    UserService userService;
    UserTestHelper userTestHelper;

    @BeforeEach
    public void before(){
        //각각 테스트 전에 모두 지운다.
        userRepository.deleteAll();
        userTestHelper = new UserTestHelper(new BCryptPasswordEncoder());
        userService = new UserService(userRepository);

    }

    @DisplayName("1. 유저를 저장한다.")
    @Test
    public void test_1(){
        //given

        User user1 = userTestHelper.createUser1();

        user1.addAuthority(new Authority(Authority.ROLE_ADMIN));
        User saveuser = userService.save(user1);

        //when

        //then
        userTestHelper.assertUser1(saveuser);
        System.out.println("========: "+ saveuser.getAuthorities().size());
    }
    //todo스 - 유저 수정,삭제 ,업데이트 테스트케이
}
