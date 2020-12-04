package kr.chis.springbootwebjpaapi.config;


import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.service.UserMapper;
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
        UserMapper admin = UserMapper.builder()
                .email("admin@mail.com")
                .name("관리자")
                .cellPhone("010-1111-2222")
                .active(true)
                .password("1111")
                .build();
        admin.addAuthority(Authority.ROLE_ADMIN);
        admin.addAuthority(Authority.ROLE_USER);
        userService.save(admin);

        UserMapper user = UserMapper.builder()
                .email("user@mail.com")
                .name("사용자")
                .cellPhone("010-1111-2222")
                .active(true)
                .password("1111")
                .build();
        user.addAuthority(Authority.ROLE_USER);
        userService.save(user);



//        try {
//            userService.deleteByEmail("admin@mail.com");
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        }

    }
}
