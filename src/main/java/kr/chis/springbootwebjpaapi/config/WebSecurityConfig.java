package kr.chis.springbootwebjpaapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public WebSecurityConfig(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTLoginFilter jwtLoginFilter = new JWTLoginFilter(objectMapper, authenticationManager());
        http
                .csrf().disable()
                .addFilter(jwtLoginFilter)
                //.authorizeRequests().antMatchers("/login").permitAll()
                //.and()
                .authorizeRequests().antMatchers("/**").authenticated()
        //        .authorizeRequests().antMatchers("/**").permitAll()
        ;


    }
    //todo-- user ID 와 비번을 받아서 토근 발급받는 URL 필요
    //todo--
    //todo security 사용지 페이징 오작동 관련 설정
}
