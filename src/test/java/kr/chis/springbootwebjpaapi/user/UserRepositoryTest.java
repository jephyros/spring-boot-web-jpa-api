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

import static org.assertj.core.api.Assertions.assertThat;

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

        //when
        User saveuser = userService.save(user1);
        //then
        userTestHelper.assertUser1(saveuser);
        //System.out.println("========: "+ saveuser.getAuthorities().size());
    }

    @DisplayName("2. 유저를 삭제한다.")
    @Test
    public void test_2(){
        //given

        User user1 = userTestHelper.createUser1();

        user1.addAuthority(new Authority(Authority.ROLE_ADMIN));
        User saveuser = userService.save(user1);

        //when
        userService.deleteByEmail(saveuser.getEmail());
        //then


        assertThat(userRepository.findAll().size()).as("유저가 삭제되서 데이터가 없다. Expect : 0").isEqualTo(0);
        //System.out.println("========: "+ saveuser.getAuthorities().size());
    }

    //todo - 유저이메일은 중복저장이안된다. 유저 수정,삭제 ,업데이트 테스트케이
}
