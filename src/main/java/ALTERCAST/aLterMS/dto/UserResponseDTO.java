package ALTERCAST.aLterMS.dto;

import ALTERCAST.aLterMS.domain.Roles;
import ALTERCAST.aLterMS.domain.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterResultDTO {
        Long Id;
        String userId;
        String name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoResultDTO {
        Long Id;
        String userId;
        String password;
        String name;
        String email;
        String phone;
        String deptName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSectionResultDTO {
        Long Id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getUserRoleResultDTO {
        Roles roles;
    }

}
