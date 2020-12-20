package kr.chis.springbootwebjpaapi.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.security.auth.login.LoginException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTLoginFilterRefreshable extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    public JWTLoginFilterRefreshable(JWTUtil jwtUtil, ObjectMapper objectMapper, AuthenticationManager authenticationManager, UserService userService){
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        //login
        setFilterProcessesUrl("/token");
    }
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //authenticationmanager
        //UsernamePasswordAuthenticationToken 을 통해 유저 ID와 패스워드를 갖 인증을 시도한다.
        LoginMapper loginMapper = objectMapper.readValue(request.getInputStream(), LoginMapper.class);

        //User Id, Password 로 로그인할경우
        if (loginMapper.getLoginType().equals(LoginMapper.LoginType.userid)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginMapper.getUsername(), loginMapper.getPassword(), null);

            return authenticationManager.authenticate(authenticationToken);
        } //리프레시 토큰으로 로그인할경우
        else if(loginMapper.getLoginType().equals(LoginMapper.LoginType.refresh)){
            if (StringUtils.isEmpty(loginMapper.getRefreshToken()))
                throw new IllegalArgumentException("리프레시토큰이 없습니다.");
            JWTVerify verify = jwtUtil.verify(loginMapper.getRefreshToken());
            if(verify.getVerify()){
                User user = userService.findByEmail(verify.getUserId()).orElseThrow(() -> new LoginException("사용자정보가 올바르지 않습니다."));

                //todo 수정중~~~~~~~~
                return null;

            }else{
                throw new TokenExpiredException("리프레시 토큰이 만료되었습니다.");
            }

        }
        else{
            throw new IllegalArgumentException("알수 없는 로그인 타입");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)  {
        User user = (User) authResult.getPrincipal();
        log.info("로그인 성공 : {}",user.getEmail());

        response.addHeader(JWTUtil.AUTH_HEADER,JWTUtil.BEARER + jwtUtil.createToken(user.getEmail(), JWTUtil.TokenType.access));
        //super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.info("로그인 실패 : {}",failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
