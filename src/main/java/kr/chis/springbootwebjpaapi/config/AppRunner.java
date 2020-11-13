package kr.chis.springbootwebjpaapi.config;


import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;


@Component
public class AppRunner implements ApplicationRunner {


    private final PasswordEncoder  passwordEncoder;
    private final UserRepository userRepository;

    public AppRunner(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        //관리자 계정을 넣는다.
        User admin = User.builder()
                .id(1L)
                .email("admin@mail.com")
                .name("관리자")
                .cellPhone("010-1111-2222")
                .authorities(new HashSet<>())
                .password(passwordEncoder.encode("1111"))
                .build();
        admin.addAuthority(Authority.ADMIN);
        userRepository.save(admin);




    }
}
