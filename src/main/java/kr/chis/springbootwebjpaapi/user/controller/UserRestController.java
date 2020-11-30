package kr.chis.springbootwebjpaapi.user.controller;

import javassist.NotFoundException;
import kr.chis.springbootwebjpaapi.common.ResponsePage;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;


    }

    @GetMapping
    public ResponsePage<User> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){

        return ResponsePage.of(userService.list(page,size));
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long userId) throws NotFoundException {
        return userService.getUser(userId).orElseThrow(()-> new NotFoundException("데이터가 없습니다."));

    }
}
