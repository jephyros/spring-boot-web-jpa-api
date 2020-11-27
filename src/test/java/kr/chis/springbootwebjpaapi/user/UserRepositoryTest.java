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
import java.util.Optional;
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

        userTestHelper = new UserTestHelper(new BCryptPasswordEncoder());
        userService = new UserService(userRepository);
        userService.deleteAll();

    }

    @DisplayName("1. 유저를 저장한다.")
    @Test
    public void test_1(){
        //given

        User user1 = userTestHelper.createUser1();

        user1.addAuthority(new Authority(Authority.ROLE_ADMIN));
        user1.addAuthority(new Authority(Authority.ROLE_USER));

        //when
        userService.save(user1);
        //then
        Optional<User> saveuser = userService.findByEmail(user1.getEmail());
        assertThat(saveuser.isPresent()).as("저장한 유저가 존재한다. Expect : true").isEqualTo(true);
        saveuser.ifPresent(user->{
            userTestHelper.assertUser1(user);
            user.getAuthorities().forEach(e-> System.out.println("======="+e.getAuthority()));

        });

    }

    @DisplayName("2. 유저를 삭제한다.")
    @Test
    public void test_2(){
        //given

        User user1 = userTestHelper.createUser1();

        user1.addAuthority(new Authority(Authority.ROLE_ADMIN));
        userService.save(user1);


        //when
        userService.deleteByEmail(user1.getEmail());
        //then
        assertThat(userService.findByEmail(user1.getEmail()).isPresent())
                .as("삭제된 유저를 조회했을경우 데이터가 없다. Expect")
                .isEqualTo(false);



    }

    //todo - 유저이메일은 중복저장이안된다. 유저 수정,삭제 ,업데이트 테스트케이
}
