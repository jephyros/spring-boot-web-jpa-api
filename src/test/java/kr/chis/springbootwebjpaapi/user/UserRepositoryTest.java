package kr.chis.springbootwebjpaapi.user;

import javassist.NotFoundException;
import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.repository.UserRepository;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    UserService userService;
    UserTestHelper userTestHelper;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void before(){
        //각각 테스트 전에 모두 지운다.

        userTestHelper = new UserTestHelper(new BCryptPasswordEncoder());
        userService = new UserService(userRepository);
        passwordEncoder = new BCryptPasswordEncoder();
        userService.deleteAll();

    }

    @DisplayName("1. 유저를 저장한다.")
    @Test
    public void test_1(){
        //given
        System.out.println("===============>:" + userRepository.findAll().size());
        User user1 = userTestHelper.createUser1();

//        user1.addAuthority(new Authority(Authority.ROLE_ADMIN));
//        user1.addAuthority(new Authority(Authority.ROLE_USER));


        //when
        User saveUser1 = userService.save(user1);
        userService.addAuthority(saveUser1,Authority.ROLE_ADMIN);
        //같은권한 두번 저장해도 추가되지않는다.
        userService.addAuthority(saveUser1,Authority.ROLE_ADMIN);
        userService.addAuthority(saveUser1,Authority.ROLE_USER);
        //총 2개의 권한을 넣는다.

        //then
        Optional<User> saveuser = userService.findByEmail(user1.getEmail());
        assertThat(saveuser.isPresent()).as("저장한 유저가 존재한다. Expect : true").isEqualTo(true);
        saveuser.ifPresent(user->{
            userTestHelper.assertUser1(user);
            assertThat(user.getAuthorities().size()).as("사용자 Role 이 2개있다. Expect : 2").isEqualTo(2);
        });

        //userRepository.deleteById(saveuser.get().getId());



    }

    @DisplayName("2. 유저를 삭제한다.")
    @Test
    public void test_2() throws NotFoundException {
        //given

        User user1 = userTestHelper.createUser1();

        User saveUser1 = userService.save(user1);

        userService.addAuthority(saveUser1,Authority.ROLE_USER);


        //when
        userService.deleteByEmail(user1.getEmail());
        //then
        assertThat(userService.findByEmail(user1.getEmail()).isPresent())
                .as("삭제된 유저를 조회했을경우 데이터가 없다. Expect")
                .isEqualTo(false);

    }

    //todo - 유저이메일은 중복저장이안된다. 유저 수정,삭제 ,업데이트 테스트케이
}
