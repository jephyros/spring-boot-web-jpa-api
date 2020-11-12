package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        User user = userTestHelper.createOneUser();
        //todo
        //user.addAuthority(Authority.ADMIN);
        userTestHelper.assertOneUser(user);

//        user.getAuthorities()
//                .stream()
//                .map(v-> System.out.printf(v.getAuthority()));

    }
}
