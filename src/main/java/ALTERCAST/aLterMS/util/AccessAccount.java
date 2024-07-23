package ALTERCAST.aLterMS.util;

import lombok.Getter;

@Getter
public class AccessAccount {

    private final String userId;
    private final boolean isAdmin;
    //todo: role 추가

    public AccessAccount(final String userId, final boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
    }
}
