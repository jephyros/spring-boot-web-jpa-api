package kr.chis.springbootwebjpaapi.user;

import javassist.NotFoundException;
import kr.chis.springbootwebjpaapi.exception.UserException;
import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.repository.UserRepository;
import kr.chis.springbootwebjpaapi.user.service.UserMapper;
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
import static org.assertj.core.api.Assertions.catchThrowable;

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

        UserMapper user1 = userTestHelper.createUserMapper("user1");


        //when

        //총 2개의 권한을 넣는다.
        //같은권한 두번 저장해도 추가되지않는다.
        user1.addAuthority(Authority.ROLE_ADMIN);
        user1.addAuthority(Authority.ROLE_ADMIN);
        user1.addAuthority(Authority.ROLE_USER);
        User saveUser1 = userService.save(user1);

        //then
        Optional<User> saveuser = userService.findByEmail(user1.getEmail());
        assertThat(saveuser.isPresent()).as("저장한 유저가 존재한다. Expect : true").isEqualTo(true);
        saveuser.ifPresent(user->{
            userTestHelper.assertUser("user1",saveUser1);
            assertThat(user.getAuthorities().size()).as("사용자 Role 이 2개있다. Expect : 2").isEqualTo(2);
        });



    }

    @DisplayName("2. 사용자정보를 삭제한다.")
    @Test
    public void test_2() {
        //given

        UserMapper user1 = userTestHelper.createUserMapper("user1");
        user1.addAuthority(Authority.ROLE_USER);
        User saveUser1 = userService.save(user1);




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
        UserMapper user1 = userTestHelper.createUserMapper("user1");
        user1.addAuthority(Authority.ROLE_USER);
        User saveUser1 = userService.save(user1);


        //when
        user1.setName("changeName");
        user1.setCellPhone("changeNumber");
        userService.modifyUser(user1);

        //then
        userService.findByEmail(saveUser1.getEmail())
                .ifPresent(
                        v->{
                            assertThat(v.getName())
                                    .as("사용자 이름이 수정되었는지 확인 Expect : " + user1.getName())
                                    .isEqualTo(user1.getName());
                            assertThat(v.getCellPhone())
                                    .as("사용자 전화번호가 수정되었는지 확인 Expect : " + user1.getCellPhone())
                                    .isEqualTo(user1.getCellPhone());
                        }
                );


    }

    //사용자 신규저장시 중복 이메일로 저장이안된다.


    @DisplayName("4. 중복이메일 저장이 안된다.")
    @Test
    public void test_4(){
        //given

        UserMapper user1 = userTestHelper.createUserMapper("user1");
        User saveUser1 = userService.save(user1);


        //when then
        UserMapper user2 = userTestHelper.createUserMapper("user1");
        user2.setEmail("user1@mail.com");
        Throwable throwable = catchThrowable(() -> userService.save(user2));

        assertThat(throwable)
                .as("중복된 이메일을 저장하려고할때 UserException 이 발생한다.")
                .isInstanceOf(UserException.class);


    }
    @DisplayName("5. 이메일 ,이름 , 전화번호는 필수 이다.")
    @Test
    public void test_5(){
        //given when then
        UserMapper user2 = userTestHelper.createUserMapper("user1");
        user2.setEmail(null);
        Throwable throwable = catchThrowable(() -> userService.save(user2));
        assertThat(throwable)
                .as("이메일이 Null 이면 UserException 이 발생한다.")
                .isInstanceOf(UserException.class);

        UserMapper user3 = userTestHelper.createUserMapper("user1");
        user3.setName(null);
        throwable = catchThrowable(() -> {
            userService.save(user3);
        });
        assertThat(throwable)
                .as("이름이 Null 이면 UserException 이 발생한다.")
                .isInstanceOf(UserException.class);

        UserMapper user4 = userTestHelper.createUserMapper("user1");
        user4.setPassword(null);
        throwable = catchThrowable(() -> {
            userService.save(user4);
        });
        assertThat(throwable)
                .as("패스워드가 Null 이면 UserException 이 발생한다.")
                .isInstanceOf(UserException.class);


    }

}
