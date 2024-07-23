package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.User;
import ALTERCAST.aLterMS.dto.UserRequestDTO;
import ALTERCAST.aLterMS.dto.UserResponseDTO;

public class UserConverter {

    public static UserResponseDTO.RegisterResultDTO toJoinResultDTO(User user) {
        return UserResponseDTO.RegisterResultDTO.builder()
                .Id(user.getId())
                .name(user.getName())
                .userId(user.getUserId())
                .build();
    }

    public static UserResponseDTO.UserInfoResultDTO toUserInfoResultDTO(User user) {
        return UserResponseDTO.UserInfoResultDTO.builder()
                .Id(user.getId())
                .name(user.getName())
                .userId(user.getUserId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .deptName(user.getDeptName())
                .password(user.getPassword())
                .build();
    }

    public static User toUser(UserRequestDTO.JoinDto request) {
        return User.builder()
                .name(request.getName())
                .userId(request.getUserId())
                .password(request.getPassword())
                .email(request.getEmail())
                .phone(request.getPhone())
                .deptName(request.getDeptName())
                .build();
    }


}
