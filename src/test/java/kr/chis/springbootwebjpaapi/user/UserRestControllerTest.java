package kr.chis.springbootwebjpaapi.user;

import kr.chis.springbootwebjpaapi.config.LoginMapper;
import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.repository.UserRepository;
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
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    private UserTestHelper userTestHelper;
    private UserService userService;


    private RestTemplate restTemplate = new RestTemplate();

    private URI uri(String path) throws URISyntaxException {
        return new URI(format("http://localhost:%d%s",port,path));
    }

    @BeforeEach
    public void before(){
        userTestHelper = new UserTestHelper(new BCryptPasswordEncoder());
        userService = new UserService(userRepository);
        userService.deleteAll();



    }

    private String getToken(String username,String password) throws URISyntaxException {
        LoginMapper loginUser = LoginMapper.builder().username(username).password(password).build();
        HttpEntity<LoginMapper> body = new HttpEntity<>(loginUser);
        ResponseEntity<String> response = restTemplate.exchange(uri("/token"), HttpMethod.POST, body, String.class);

        System.out.println("==========");
        return response.getHeaders().get("authorization").toString();
    }

    @DisplayName("1. Admin 유저는 UserList를 가져올수있다.")
    @Test
    public void test_1() throws URISyntaxException {
        //given
        User user1 = userTestHelper.createUser("user1");

        User saveUser = userService.save(user1);
        userService.addAuthority(saveUser, Authority.ROLE_ADMIN);

        userTestHelper.assertUser("user1",saveUser);



        //System.out.println("========passwoord" + user1.getPassword());

        String token = getToken(user1.getEmail(), "user11234");
        System.out.println("========" + token);

        //when

        //then

    }


    //todo Rest Controller Integration Test

}
