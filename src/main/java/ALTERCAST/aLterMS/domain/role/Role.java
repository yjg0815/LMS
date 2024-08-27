package ALTERCAST.aLterMS.domain.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "name") // name을 dtype 칼럼으로 설정
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class Role implements GrantedAuthoritiesContainer {
    /**
     * Role에 주어진 권한 목록
     */
/*    @Convert(converter = StringCollectionConverter.class) // Collection => String 으로 변환하여 하나의 컬럼에 저장
    @Enumerated(EnumType.STRING)
    @Column
    */
    @Getter
    @Transient //비즈니스 로직에서만 사용, 영속 대상에서 제외되는 컬럼
    protected Privilege allowedPrivileges;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(insertable = false, updatable = false) //읽기 전용, insert, update 불가
    @Getter
    private String name;

    /**
     * 자신의 역할과 Privilege를 담은 Collection을 반환한다
     */
    @Override
    public Collection<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!name.isBlank())
            authorities.add(new SimpleGrantedAuthority("ROLE_" + name.toUpperCase()));

        return authorities;
    }

    public boolean isRole(String roleName) {
        return this.getClass().getSimpleName().equalsIgnoreCase(roleName);
    }

    public String getRoleName() {
        return this.getClass().getSimpleName();
    }
}
