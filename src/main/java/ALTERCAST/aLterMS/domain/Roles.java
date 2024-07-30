package ALTERCAST.aLterMS.domain;

import ALTERCAST.aLterMS.domain.role.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class Roles {

    private List<Role> roles;

    public Role findRole(final String roleName) {
        return roles.stream()
                .filter(each -> each.isRole(roleName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("역할을 찾지 못함"));
    }
}
