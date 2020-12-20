package kr.chis.springbootwebjpaapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import kr.chis.springbootwebjpaapi.config.JWTUtil;
import kr.chis.springbootwebjpaapi.config.JWTVerify;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Profile("test")
public class JWTTokenTest {
    @DisplayName("1. JWT 토큰이 잘만들어진다.")
    @Test
    public void test_1(){
        Algorithm al = Algorithm.HMAC512("secretKey");

        String token = JWT.create()
                .withSubject("test@mail.com")
                .withClaim("exp", Instant.now().getEpochSecond() + 5)
                .sign(al);


        //토큰이잘못된경우 테스트 예외 발생(secretKey 차이발생)
        //al = Algorithm.HMAC512("secretKey"+"error");
        Algorithm al_err = Algorithm.HMAC512("secretKey"+"err");
        assertThrows(JWTVerificationException.class, () -> {
            JWT.require(al_err).build().verify(token);
        });

        DecodedJWT verify = JWT.require(al).build().verify(token);
        System.out.println(verify.getHeaderClaim("typ").asString());
        System.out.println(verify.getHeaderClaim("alg").asString());
        verify.getClaims().forEach(this::printClaim);

    }
    @DisplayName("2. JWT Util 토큰생성 및 검증함수")
    @Test
    public void test_2(){
        JWTUtil jwtUtil = new JWTUtil();
        String token = jwtUtil.createToken("user@mail.com", JWTUtil.TokenType.access);
        JWTVerify verify = jwtUtil.verify(token);
        JWTVerify verify2 = jwtUtil.verify(token+"err");

        assertThat(verify.getVerify()).as("정상 토큰 검증 Expect : true").isEqualTo(true);
        assertThat(verify2.getVerify()).as("오류 토큰검증 Expect : false").isEqualTo(false);


    }
    private void printClaim(String key, Claim claim){
        if(claim.asDate() != null){
            System.out.printf("%s : %s\n",key,claim.asDate().toString());
        }
        if(claim.asString() != null){
            System.out.printf("%s : %s\n",key,claim.toString());
        }
    }

}
