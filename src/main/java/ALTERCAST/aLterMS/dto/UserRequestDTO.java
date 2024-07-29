package ALTERCAST.aLterMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class UserRequestDTO {

    @Getter
    public static class JoinDto {

        @NotBlank
        @Size(min = 1, max = 6)
        String name;

        @NotBlank
        @Size(min = 5, max = 10)
        String userId;

        @NotBlank
        @Size(min = 5)
        String password;

        String email;
        String phone;
        String deptName;
    }

    @Getter
    public static class loginDto {

        String userId;
        String password;
    }

    @Getter
    public static class UpdateInfoDto {

        @Size(min = 1, max = 6)
        String name;

        @Size(min = 5, max = 20)
        String userId;

        @Size(min = 4)
        String password;

        String email;
        String phone;
        String deptName;
    }

    @Getter
    public static class SelectUserSectionDto {

        Long secId;
        String role;
    }
}
