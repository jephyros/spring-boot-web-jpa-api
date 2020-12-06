package kr.chis.springbootwebjpaapi.user.controller;

import javassist.NotFoundException;
import kr.chis.springbootwebjpaapi.common.ResponsePage;
import kr.chis.springbootwebjpaapi.exception.ErrorCode;
import kr.chis.springbootwebjpaapi.exception.UserException;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.service.UserMapper;
import kr.chis.springbootwebjpaapi.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.security.AccessControlException;

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
    public User getUser(@PathVariable("id") Long userId) {
        return userService.findById(userId).orElseThrow(()-> new UserException(ErrorCode.USER_DATA_NOT_FOUND));

    }
    @PostMapping
    public User saveUser(@RequestBody UserMapper userMapper) {

        //todo UserMapper 필수정보 체크 할것
        return  userService.save(userMapper);

    }
    @PutMapping
    public User updateUser(@RequestBody UserMapper userMapper) {
        //사용자 이름 전화번호 수정
        //todo UserMapper 필수정보 체크 할것
        return  userService.modifyUser(userMapper);

    }
}
