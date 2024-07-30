package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Roles;
import ALTERCAST.aLterMS.domain.Section;
import ALTERCAST.aLterMS.domain.User;
import ALTERCAST.aLterMS.domain.UserSection;
import ALTERCAST.aLterMS.domain.role.Role;
import ALTERCAST.aLterMS.dto.UserRequestDTO;
import ALTERCAST.aLterMS.dto.UserResponseDTO;

import java.util.List;

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

    public static UserSection toUserSection(User user, Section section, Role role) {
        return UserSection.builder()
                .user(user)
                .section(section)
                .role(role)
                .build();
    }

    public static List<UserResponseDTO.UserSectionResultDTO> toUserSectionResultDTO(List<UserSection> userSections) {
        return userSections.stream()
                .map(each -> UserResponseDTO.UserSectionResultDTO.builder()
                        .Id(each.getId()).build())
                .toList();

    }

    public static UserResponseDTO.getUserRoleResultDTO toGetUserRoleResultDTO(Roles roles) {
        return UserResponseDTO.getUserRoleResultDTO.builder().roles(roles).build();
    }

}
