package kr.chis.springbootwebjpaapi.config;

import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
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
    protected void configure(HttpSecurity http) throws Exception {
        JWTLoginFilter jwtLoginFilter = new JWTLoginFilter();
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
