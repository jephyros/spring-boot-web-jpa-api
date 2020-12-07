package kr.chis.springbootwebjpaapi.config;

import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author InSeok
 * Date : 2020/11/24
 * Remark :
 */
public class JWTCheckFilter extends BasicAuthenticationFilter {
    private final JWTUtil jwtUtil;
    private final UserService userService;
    public JWTCheckFilter(JWTUtil jwtUtil,UserService userService, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(JWTUtil.AUTH_HEADER);
        //토큰이 없으면 security chain으로 넘긴다. 인증정보가없으므로 인증실패됨
        if(token == null || !token.startsWith(JWTUtil.BEARER)){
            chain.doFilter(request,response);
            return;
        }
        JWTVerify verify = jwtUtil.verify(token.substring(JWTUtil.BEARER.length()));
        if(verify.getVerify()){
            //인증에 성공했으면
            Optional<User> optionalUser = userService.findByEmail(verify.getUserId());
            //유저정보를 SecurityContextHolder 에 넣는다.
            optionalUser.ifPresent( user-> SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities())
            ));

        }
        chain.doFilter(request,response);

        super.doFilterInternal(request, response, chain);
    }
}
