package kr.chis.springbootwebjpaapi.user.service;

import javassist.NotFoundException;
import kr.chis.springbootwebjpaapi.exception.ErrorCode;
import kr.chis.springbootwebjpaapi.exception.UserException;
import kr.chis.springbootwebjpaapi.user.repository.Authority;
import kr.chis.springbootwebjpaapi.user.repository.User;
import kr.chis.springbootwebjpaapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
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
