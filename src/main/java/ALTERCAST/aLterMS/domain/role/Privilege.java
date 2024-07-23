package ALTERCAST.aLterMS.domain.role;

import lombok.Getter;
import org.springframework.stereotype.Component;

public enum Privilege {
    INSTRUCTOR,
    STUDENT;

    /**
     * static Bean 지정: SpEL에서 쉽게 지정하기 위한 용도<br>
     * 이걸 지정함으로써 SpEL에서 com.ssssogong.issuemanager.domain.role.Privilege 대신 @Privilege로 지정 가능
     */
    @Component("Privilege")
    @Getter
    static class SpringComponent {
        private final Privilege INSTRUCTOR = Privilege.INSTRUCTOR;
        private final Privilege STUDENT = Privilege.STUDENT;
    }
}
