package kr.chis.springbootwebjpaapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.chis.springbootwebjpaapi.config.LoginMapper;
import kr.chis.springbootwebjpaapi.user.UserTestHelper;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.service.UserMapper;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class JWTLoginTest {
    //todo 로그인 및 토큰 받아오는 테스트
    @LocalServerPort
    private int port;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    private RestTemplate restTemplate;
    private UserTestHelper userTestHelper;

    @BeforeEach()
    public void before(){
        userService.deleteAll();
        restTemplate = new RestTemplate();
        userTestHelper = new UserTestHelper(new BCryptPasswordEncoder());


    }

    private URI uri(String path) throws URISyntaxException {
        return new URI(format("http://localhost:%d%s",port,path));
    }

    @DisplayName("1. 아이디 패스워드를 통하여 토큰을 받아온다.")
    @Test
    public void test_1() throws Exception {
        //given
        //db데이터 user 데이터저장
        UserMapper user1 = userTestHelper.createUserMapper("user1");
        userService.save(user1);

        LoginMapper loginUser = LoginMapper.builder().username(user1.getEmail()).password("user11234").build();
        HttpEntity<LoginMapper> body = new HttpEntity<>(loginUser);
        ResponseEntity<String> response = restTemplate.exchange(uri("/token"), HttpMethod.POST, body, String.class);

        assertThat(response.getHeaders().get("authorization").toString())
                .as("Authorization 헤더값이 존재한다.")
                .contains("Bearer ")
                .isNotNull();

    }
    @DisplayName("2. 패스워드가 잘못될경우 Exception을 발생한다.")
    @Test
    public void test_2() throws Exception {
        //given
        //db데이터 user 데이터저장
        UserMapper user1 = userTestHelper.createUserMapper("user1");
        userService.save(user1);

        //패스워드가 틀릴경우

        Throwable catchThrowable = catchThrowable(() -> {
            LoginMapper wrongUser = LoginMapper.builder().username(user1.getEmail()).password("user11234error").build();

            HttpEntity<LoginMapper> wrongBody = new HttpEntity<>(wrongUser);

            restTemplate.exchange(uri("/token"), HttpMethod.POST, wrongBody, String.class);

        });
        assertThat(catchThrowable).as("로그인실패 에러가난다.")
                //.isInstanceOf(Exception.class)
                .isInstanceOf(HttpClientErrorException.class);

    }

}
