package ALTERCAST.aLterMS.util;

import lombok.Getter;

@Getter
public class AccessAccount {

    private final String userId;
    //todo: role 추가

    public AccessAccount(final String userId) {
        this.userId = userId;
    }
}
