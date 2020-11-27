package kr.chis.springbootwebjpaapi.config;


import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AppRunner implements ApplicationRunner {


    private final PasswordEncoder  passwordEncoder;

    @Autowired
    private UserService userService;


    public AppRunner(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void run(ApplicationArguments args) {

//        //관리자 계정을 넣는다.
        User admin = User.builder()
                .email("admin@mail.com")
                .name("관리자")
                .cellPhone("010-1111-2222")
                .active(true)
                .password(passwordEncoder.encode("1111"))
                .build();
        User saveAdmin = userService.save(admin);
        userService.addAuthority(saveAdmin,Authority.ROLE_ADMIN);
        userService.addAuthority(saveAdmin,Authority.ROLE_USER);

        User user = User.builder()
                .email("user@mail.com")
                .name("사용자")
                .cellPhone("010-1111-2222")
                .active(true)
                .password(passwordEncoder.encode("1111"))
                .build();
        User saveUser = userService.save(user);
        saveUser.addAuthority(new Authority(Authority.ROLE_USER));
        userService.save(saveUser);

        //todo delete check
        //userService.deleteByEmail("user@mail.com");

    }
}
