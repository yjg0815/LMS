package ALTERCAST.aLterMS.security;


import ALTERCAST.aLterMS.domain.User;
import ALTERCAST.aLterMS.repository.UserRepository;
import ALTERCAST.aLterMS.repository.UserSectionRepository;
import ALTERCAST.aLterMS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserDetailsService 인터페이스 구현 <br>
 * (UserDetailsService : Spring Security 내부에서 현재 사용자 정보 로드할 때 사용하는 인터페이스)
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    public final UserService userService;
    public final UserSectionRepository userSectionRepository;
    public final UserRepository userRepository;

    /**
     * username(=사용자 id)에 해당하는 UserDetails 객체를 반환하는 메소드 <br>
     * (UserDetails : 사용자 인증 정보를 담음)
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailsService: load username '" + userId + "'");
        // DB에서 사용자를 로드한다
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자 id를 찾을 수 없습니다 (id=" + userId + ")");
        }
        User user = optionalUser.get();
        // CutsomUserDetails에 담아서 반환한다
        CustomUserDetails userDetails = new CustomUserDetails(user, userSectionRepository, userService);
        return userDetails;
    }
}
