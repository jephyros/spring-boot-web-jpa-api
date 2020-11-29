package kr.chis.springbootwebjpaapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.chis.springbootwebjpaapi.user.repository.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public JWTLoginFilter(JWTUtil jwtUtil ,ObjectMapper objectMapper, AuthenticationManager authenticationManager){
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        //login
        setFilterProcessesUrl("/token");
    }
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //authenticationmanager
        //UsernamePasswordAuthenticationToken 을 통해 유저 ID와 패스워드를 갖 인증을 시도한다.
        LoginMapper loginMapper = objectMapper.readValue(request.getInputStream(), LoginMapper.class);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginMapper.getUsername(), loginMapper.getPassword(),null);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        log.info("로그인 성공 : {}",user.getEmail());

        response.addHeader("authorization","Bearer " + jwtUtil.createToken(user.getEmail()));
        //super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.info("로그인 실패 : {}",failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
