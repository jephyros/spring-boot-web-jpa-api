package kr.chis.springbootwebjpaapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;

/**
 * @author InSeok
 * Date : 2020/11/24
 * Remark :
 */
public class JWTUtil {
    public static final String AUTH_HEADER = "authorization";
    public static final String REFRESH_HEADER ="refresh-token ";
    public static final String BEARER ="Bearer ";


    private String secretKey ="secretKey";
    private Algorithm al = Algorithm.HMAC512(secretKey);
    private long lifeTime= 1800;

    public String createToken(String userId){
        return JWT.create()
                .withSubject(userId)
                .withClaim("exp", Instant.now().getEpochSecond() + lifeTime)
                .sign(al);

    }
    public JWTVerify verify(String token){

        try {
            DecodedJWT verify = JWT.require(al).build().verify(token);
            return JWTVerify.builder().userId(verify.getSubject()).verify(true).build();
        }catch (JWTVerificationException e){
            DecodedJWT decode = JWT.decode(token);
            return JWTVerify.builder().userId(decode.getSubject()).verify(false).build();

        }
    }
}
