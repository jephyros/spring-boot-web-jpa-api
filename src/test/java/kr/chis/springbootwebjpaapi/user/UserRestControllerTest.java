package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest
@ActiveProfiles("test")
public class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @DisplayName("1. Get /avi/v1/user 은 누구나 접근이가능하다.")
    @Test
    public void test_1() throws Exception {
        // todo webmvctest
//        mockMvc.perform(get("/avi/v1/user"))
//                .andExpect(status().is4xxClientError());


    }



}
