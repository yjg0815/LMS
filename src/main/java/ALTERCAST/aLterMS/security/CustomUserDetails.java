package ALTERCAST.aLterMS.security;

import ALTERCAST.aLterMS.domain.User;
import ALTERCAST.aLterMS.domain.UserSection;
import ALTERCAST.aLterMS.repository.UserSectionRepository;
import ALTERCAST.aLterMS.service.UserService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * 사용자 인증 관련 정보를 반환하는 인터페이스 <br>
 * <i>(UserDetails 인터페이스 구현 -> Spring Security에서 사용자 정보를 확인할 때 사용)</i>
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter
    private final User user;
    private final UserSectionRepository userSectionRepository;
    private final UserService userService;
    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    /**
     * 해당 User의 권한 반환 <br>
     * User는 section마다 다른 권한을 가지는데, Privilege 뒤에 TO_SECTIONAME 을 붙여 SECTION 별 권한을 구분한다
     */
    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // UserSection에서 해당 User의 Role을 전부 가져온다.
        System.out.println("CustomUserDetails: getting authorities for user " + user.getUserId() + " (id: " + user.getId() + ")");
        authorities = userService.getAuthorities(user.getId());
        System.out.print("CustomUserDetails: got [ ");
        authorities.iterator().forEachRemaining((auth) -> System.out.print(auth.getAuthority() + " "));
        System.out.println("]");
        return authorities;
    }

    /**
     * User가 해당 section에서 갖는 권한을 반환한다.
     *
     * @param secId 권한을 검색하고자 하는 프로젝트의 id
     */
    public Collection<? extends GrantedAuthority> getAuthorities(Long secId) {
        Collection<GrantedAuthority> ret = new ArrayList<>();
        // userId와 secId 맞는 UserSection 객체를 검색한다
        Optional<UserSection> optionalUserSection = userSectionRepository.findByIdAndSectionId(user.getId(), secId);
        // 검색된 게 없으면 빈 권한을 반환한다
        if (optionalUserSection.isEmpty()) return new ArrayList<>();
        // 저장된 권한을 그대로 반환한다
        UserSection userSection = optionalUserSection.get();
        ret.addAll(userSection.getRole().getGrantedAuthorities());
        return ret;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
