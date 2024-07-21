package ALTERCAST.aLterMS.dto;

import lombok.Getter;

public class StudentRequestDTO {

    @Getter
    public static class JoinDto {
        String name;
        String userId;
        String password;
        String email;
        String phone;
        String deptName;
    }
}
