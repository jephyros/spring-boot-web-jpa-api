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
    @DisplayName("1. 유저 권한을 추가 및 삭제 한다.")
    @Test
    public void test_1(){
        User user = userTestHelper.createUser1();

        //1개추가
        user.addAuthority(new Authority(Authority.ROLE_ADMIN));
        assertThat(user.getAuthorities().size()).as("관리자 권한 1개추가 expect : 1").isEqualTo(1);

        //같은 권한 중복 추가 안되는지 테스트
        user.addAuthority(new Authority(Authority.ROLE_ADMIN));
        assertThat(user.getAuthorities().size()).as("관리자 권한 중복 추가시 추가안되는지 expect : 1").isEqualTo(1);

        //1개추가 하여 2개
        user.addAuthority(new Authority(Authority.ROLE_USER));
        assertThat(user.getAuthorities().size()).as("권한 2개 추가 expect : 2").isEqualTo(2);

        //권한 1개 삭제 하여 1개 남음
        user.removeAuthority(new Authority(Authority.ROLE_ADMIN));
        assertThat(user.getAuthorities().size()).as("권한 1개삭제 expect : 1").isEqualTo(1);
        user.getAuthorities().forEach(v->{
            assertThat(v.getAuthority()).as("권한 1개삭제후 남은 권한 expect : ROLE_USER").isEqualTo(Authority.ROLE_USER);

        });

    }

}
