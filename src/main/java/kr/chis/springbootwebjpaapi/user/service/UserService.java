package kr.chis.springbootwebjpaapi.user.service;

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

    public Page<User> list(Integer page, Integer size){
        return userRepository.findAll(PageRequest.of(page-1,size));
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
