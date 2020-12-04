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

    @DisplayName("1. 사용자를 신규 저장한다.")
    @Test
    public void test_1(){
        //given

        User user1 = userTestHelper.createUser("user1");

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
            userTestHelper.assertUser("user1",user1);
            assertThat(user.getAuthorities().size()).as("사용자 Role 이 2개있다. Expect : 2").isEqualTo(2);
        });



    }

    @DisplayName("2. 사용자정보를 삭제한다.")
    @Test
    public void test_2() {
        //given

        User user1 = userTestHelper.createUser("user1");

        User saveUser1 = userService.save(user1);

        userService.addAuthority(saveUser1,Authority.ROLE_USER);


        //when
        userService.deleteByEmail(user1.getEmail());
        //then
        assertThat(userService.findByEmail(user1.getEmail()).isPresent())
                .as("삭제된 유저를 조회했을경우 데이터가 없다. Expect")
                .isEqualTo(false);

    }

    //사용 이름,전화번호를 수정한다.

    @DisplayName("3. 사용자 이름,전화번호를 수정한다.")
    @Test
    public void test_3() {
        //given
        User user1 = userTestHelper.createUser("user1");
        User saveUser1 = userService.save(user1);
        userService.addAuthority(saveUser1,Authority.ROLE_USER);

        //when
        saveUser1.setName("changeName");
        saveUser1.setCellPhone("changeNumber");
        userService.modifyUser(saveUser1);

        //then
        userService.findByEmail(saveUser1.getEmail())
                .ifPresent(
                        v->{
                            assertThat(v.getName())
                                    .as("사용자 이름이 수정되었는지 확인 Expect : " + saveUser1.getName())
                                    .isEqualTo(saveUser1.getName());
                            assertThat(v.getCellPhone())
                                    .as("사용자 전화번호가 수정되었는지 확인 Expect : " + saveUser1.getCellPhone())
                                    .isEqualTo(saveUser1.getCellPhone());
                        }
                );


    }

    //사용자 신규저장시 중복 이메일로 저장이안된다.


    //todo - 유저이메일은 중복저장이안된다. 유저 수정,삭제 ,업데이트 테스트케이
}
