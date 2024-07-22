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
        String userId;

        @NotBlank
        @Size(min = 5, max = 12)
        String password;

        String email;
        String phone;
        String deptName;
    }
}
