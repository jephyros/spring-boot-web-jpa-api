package kr.chis.springbootwebjpaapi.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.chis.springbootwebjpaapi.common.ResponsePageImpl;
import kr.chis.springbootwebjpaapi.config.LoginMapper;
import kr.chis.springbootwebjpaapi.user.repository.Authority;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private UserTestHelper userTestHelper;



    private RestTemplate restTemplate = new RestTemplate();

    private URI uri(String path) throws URISyntaxException {
        return new URI(format("http://localhost:%d%s",port,path));
    }

    @BeforeEach
    public void before(){
        userTestHelper = new UserTestHelper(passwordEncoder);
        userService.deleteAll();

    }

    private String getToken(String username,String password) throws URISyntaxException {
        LoginMapper loginUser = LoginMapper.builder().username(username).password(password).build();
        HttpEntity<LoginMapper> body = new HttpEntity<>(loginUser);
        ResponseEntity<String> response = restTemplate.exchange(uri("/token"), HttpMethod.POST, body, String.class);

        return response.getHeaders().get("authorization").toString();
    }

    @DisplayName("1. Admin 유저는 UserList를 가져올수있다.")
    @Test
    public void test_1() throws URISyntaxException, JsonProcessingException {
        //given
        UserMapper user1 = userTestHelper.createUserMapper("user1");
        user1.addAuthority(Authority.ROLE_ADMIN);
        User saveUser = userService.save(user1);

        UserMapper user2 = userTestHelper.createUserMapper("user2");
        user2.addAuthority(Authority.ROLE_USER);
        userService.save(user2);

        userTestHelper.assertUser("user1",saveUser);
        String token = getToken(user1.getEmail(), "user11234").substring(("Bearer ").length()+1);


        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<String> response = restTemplate.exchange(uri("/api/v1/users"), HttpMethod.GET, entity, String.class);

        ResponsePageImpl<User> page = objectMapper.readValue(response.getBody(),
                new TypeReference<ResponsePageImpl<User>>() {
                });
        assertThat(page.getTotalElements()).as("DB상의 유저수는 2명이다. Expect : 2").isEqualTo(2);


        assertThat(page.getContent().stream().map(User::getEmail)
                .collect(Collectors.toSet()).containsAll(Set.of("user1@mail.com","user2@mail.com")))
                .as("UserList는 'user1@mail.com'과 'user2@mail.com' 을 포함한다. Expect : true")
                .isEqualTo(true);




        //then

    }

    //개별조회

    //개별조회시 데이터없을겨우 에러 반환

    //사용자 이름,전화번호를 수정한다.


    //관리자가 사용자를 삭제한다.


    //todo Rest Controller Integration Test

}
