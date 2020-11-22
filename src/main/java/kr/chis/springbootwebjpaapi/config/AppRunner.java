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
                //.id(1L)
                .email("admin@mail.com")
                .name("관리자샘플")
                .cellPhone("010-1111-2222")
                .active(true)
                .authorities(new HashSet<>())
                .password(passwordEncoder.encode("1111"))
                .build();
        userRepository.save(admin);
        userRepository.findByEmail(admin.getEmail())
                .ifPresent(user->{
                    user.addAuthority(new Authority(Authority.ROLE_ADMIN));
                    user.addAuthority(new Authority(Authority.ROLE_USER));
                    userRepository.save(user);
                });

        User user = User.builder()
                //.id(2L)
                .email("user@mail.com")
                .name("사용자샘플")
                .cellPhone("010-1111-2222")
                .active(true)
                .authorities(new HashSet<>())
                .password(passwordEncoder.encode("1111"))
                .build();
        User saveUser = userRepository.save(user);
        saveUser.addAuthority(new Authority(Authority.ROLE_ADMIN));
        userRepository.save(saveUser);



    }
}
