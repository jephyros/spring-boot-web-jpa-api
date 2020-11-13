package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author InSeok
 * Date : 2020/11/12
 * Remark :
 */


public class UserTest {


    UserTestHelper userTestHelper;

    @BeforeEach
    public void before(){
        userTestHelper = new UserTestHelper(new BCryptPasswordEncoder());
    }
    @DisplayName("1. 유저 권한을 추가한다.")
    @Test
    public void test_1(){
        User user = userTestHelper.createUser1();

        //1개추가
        user.addAuthority(Authority.ADMIN);
        assertThat(1).as("expect : 1").isEqualTo(user.getAuthorities().size());

        //같은 권한 중복 추가 안되는지 테스트
        user.addAuthority(Authority.ADMIN);
        assertThat(1).as("expect : 1").isEqualTo(user.getAuthorities().size());

        //1개추가 하여 2개
        user.addAuthority(Authority.USER);
        assertThat(2).as("expect : 2").isEqualTo(user.getAuthorities().size());

    }
}
