package kr.chis.springbootwebjpaapi.user.service;

import kr.chis.springbootwebjpaapi.exception.ErrorCode;
import kr.chis.springbootwebjpaapi.exception.UserException;
import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    //private final PasswordEncoder passwordEncoder;

    //신규 유저 저장
    public User save(UserMapper userMapper){
        User user = userRepository.save(userMapper.convertUser(passwordEncoder));
        userMapper.getAuthorities().forEach(
                auth->{
                    addAuthority(user,auth);
                }
        );

        return user;
    }

    //사용자의 이름 ,전화번호를 수정한다.
    public User modifyUser(UserMapper user){
        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserException(ErrorCode.USER_DATA_NOT_FOUND));
        findUser.setName(user.getName());
        findUser.setCellPhone(user.getCellPhone());
        return userRepository.save(findUser);

    }
    public User addAuthority(User user,String authority){
        //todo user_id is null 일경우 에러
        user.addAuthority(new Authority(authority));
        return userRepository.save(user);
    }

    public User removeAuthority(User user,String authority){
        //todo user_id is null 일경우 에러
        user.removeAuthority(new Authority(authority));
        return userRepository.save(user);
    }

    public User deleteByEmail(String email)  {

         return userRepository.findByEmail(email)
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                }).orElseThrow(()-> new UserException(ErrorCode.USER_DATA_NOT_FOUND));

    }

    public Page<User> list(Integer page, Integer size){
        return userRepository.findAll(PageRequest.of(page-1,size));
    }

    public Optional<User> findById(Long userId)  {
        return userRepository.findById(userId);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("잘못된 로그인정보입니다."));
    }
    //사용자 권한추가
    public User addAuthority(String email,String authority){
        return userRepository.findByEmail(email)
                .map(user -> user.addAuthority(new Authority(authority)))
                .orElseThrow(() -> new RuntimeException("사용자정보가 정확하지않습니다."));
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

}
