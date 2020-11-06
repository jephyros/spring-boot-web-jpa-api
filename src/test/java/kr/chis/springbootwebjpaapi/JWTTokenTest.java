package kr.chis.springbootwebjpaapi;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;



@SpringBootTest
public class JWTTokenTest {
    @DisplayName("1. JWT 토큰이 잘만들어진다.")
    @Test
    public void test_1(){
        Algorithm al = Algorithm.HMAC512("secretKey");

        String token = JWT.create()
                .withSubject("test@mail.com")
                .withClaim("exp", Instant.now().getEpochSecond() + 5)
                .sign(al);

        System.out.println(token);

        //assertThrows()
        //토큰이오류이면 예외 발생
        JWT.require(al).build().verify(token);



    }
}
