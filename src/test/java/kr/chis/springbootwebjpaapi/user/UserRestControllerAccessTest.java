package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.common.ResponsePage;
import kr.chis.springbootwebjpaapi.config.WebSecurityConfig;
import kr.chis.springbootwebjpaapi.user.controller.UserRestController;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.repository.UserRepository;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;




@WebMvcTest(UserRestController.class)
@ActiveProfiles("test")
@Import(WebSecurityConfig.class)
public class UserRestControllerAccessTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;


    UserTestHelper userTestHelper;

    @BeforeEach
    public void before(){
        userTestHelper= new UserTestHelper(new BCryptPasswordEncoder());
    }

    @DisplayName("1. 인증 정보가 없을경우 접근이 불가능하다.")
    @Test
    public void test_1() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("2. 인증 정보가 있을때 접근이 가능하다.")
    @Test
    public void test_2() throws Exception {
        User admin = userTestHelper.createAdminRoleUser("user1");
        //given(userService.list(0,0)).willReturn(Page.empty());
        //System.out.println("---------admin:" + admin.getAuthorities().size());
        mockMvc.perform(get("/api/v1/users").with(user(admin)))
                .andExpect(status().is2xxSuccessful());
    }
    // todo -관리자 권한, 유저권한



}
