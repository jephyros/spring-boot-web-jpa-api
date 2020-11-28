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
    private JWTUtil jwtUtil = new JWTUtil();

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
        JWTLoginFilter jwtLoginFilter = new JWTLoginFilter(jwtUtil ,objectMapper, authenticationManager());
        JWTCheckFilter jwtCheckFilter = new JWTCheckFilter(jwtUtil,userService,authenticationManager());

        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/users/**").permitAll().and()
                .addFilter(jwtLoginFilter)
                .addFilter(jwtCheckFilter)

                .authorizeRequests().antMatchers("/**").authenticated()
        //        .authorizeRequests().antMatchers("/**").permitAll()
        ;


    }


}
