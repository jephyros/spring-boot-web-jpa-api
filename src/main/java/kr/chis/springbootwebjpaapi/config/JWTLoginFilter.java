package kr.chis.springbootwebjpaapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    //todo jwt 로그인 처리
    public JWTLoginFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager){
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        //login
        setFilterProcessesUrl("/token");
    }
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //authenticationmanager
        //UsernamePasswordAuthenticationToken 로 인증후 토큰을 발행한다.
        LoginMapper loginMapper = objectMapper.readValue(request.getInputStream(), LoginMapper.class);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginMapper.getUsername(), loginMapper.getPassword(),null);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("로그인성공==========");
        //super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("로그인실패===========" + failed.getMessage());
        //super.unsuccessfulAuthentication(request, response, failed);
    }
}
