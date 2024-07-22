package ALTERCAST.aLterMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class StudentRequestDTO {

    @Getter
    public static class JoinDto {

        @NotBlank
        @Size(min = 1, max = 6)
        String name;

        @NotBlank
        @Size(min = 5, max = 10)
        String stuId;

        @NotBlank
        @Size(min = 5)
        String password;

        String email;
        String phone;
        String deptName;
    }

    @Getter
    public static class UpdateInfoDto {

        @Size(min = 1, max = 6)
        String name;

        @Size(min = 5, max = 10)
        String stuId;

        @Size(min = 5)
        String password;

        String email;
        String phone;
        String deptName;
    }
}
