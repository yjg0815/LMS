package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.converter.SectionConverter;
import ALTERCAST.aLterMS.converter.UserConverter;
import ALTERCAST.aLterMS.domain.User;
import ALTERCAST.aLterMS.domain.UserSection;
import ALTERCAST.aLterMS.dto.SectionResponseDTO;
import ALTERCAST.aLterMS.dto.UserRequestDTO;
import ALTERCAST.aLterMS.dto.UserResponseDTO;
import ALTERCAST.aLterMS.service.SectionService;
import ALTERCAST.aLterMS.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SectionService sectionService;

    //회원가입
    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "아이디, 이름, Long id 반환")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "아이디 중복", content = @Content(schema = @Schema(implementation = ApiResponse.class))),

    })
    public ApiResponse<UserResponseDTO.RegisterResultDTO> join(@Valid @RequestBody UserRequestDTO.JoinDto request) {
        User user = userService.joinUser(request);
        return ApiResponse.of(SuccessStatus._CREATED, UserConverter.toJoinResultDTO(user));
    }

    // 로그인은 로그인 필터 통해서 자동으로 권한 확인

    /**
     * login filter 거쳐서 로그인 됨 (여기는 도달할 필요 없음)
     */
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<Object> login(@RequestBody UserRequestDTO.loginDto request) {
        return ResponseEntity.ok().body("good job");
    }

    //학생 정보 조회
    @GetMapping()
    @Operation(summary = "유저 정보 조회", description = "해당 유저 모든 정보 반환, 유저 본인만 확인 가능")
    public ApiResponse<UserResponseDTO.UserInfoResultDTO> getUserInfo() {
        User user = userService.getInfoOfUser();
        return ApiResponse.of(SuccessStatus.GET_USER_INFO, UserConverter.toUserInfoResultDTO(user));
    }

    //유저 정보 수정
    @PutMapping()
    @Operation(summary = "유저 정보 수정", description = "수정된 정보 반환, 유저 본인만 가능")
    public ApiResponse<UserResponseDTO.UserInfoResultDTO> updateUserInfo(@RequestBody @Valid UserRequestDTO.UpdateInfoDto request) {
        User user = userService.updateInfoOfUser(request);
        return ApiResponse.of(SuccessStatus.UPDATE_USER_INFO, UserConverter.toUserInfoResultDTO(user));
    }

    //유저 탈퇴
    @DeleteMapping()
    @Operation(summary = "유저 탈퇴", description = "Long Id 반환, 유저 본인만 가능")
    public ApiResponse<UserResponseDTO.RegisterResultDTO> deleteUserInfo() {
        User user = userService.deleteInfoOfUser();
        return ApiResponse.of(SuccessStatus.DELETE_USER_INFO, UserConverter.toJoinResultDTO(user));
    }

    //역할 선택
    @PostMapping("/set/{userId}")
    @Operation(summary = "역할 및 강의 선택", description = "교수 or 학생 선택 and section 선택")
    public ApiResponse<List<UserResponseDTO.UserSectionResultDTO>> selectRole(@PathVariable(value = "userId") String userId,
                                                                              @RequestBody @Valid List<UserRequestDTO.SelectUserSectionDto> request) {
        List<UserSection> userSections = userService.createUserSection(userId, request);
        return ApiResponse.of(SuccessStatus.CREATE_USER_SECTION, UserConverter.toUserSectionResultDTO(userSections));
    }

    @GetMapping("/set/roles")
    @Operation(summary = "현재 존재하는 Role 가져오기")
    public ApiResponse<UserResponseDTO.getUserRoleResultDTO> fetchRoles() {
        return ApiResponse.of(SuccessStatus.GET_ROLE_INFO, UserConverter.toGetUserRoleResultDTO(userService.getRoles()));
    }

    @GetMapping("/set/sections")
    @Operation(summary = "전체 section 가져오기")
    public ApiResponse<List<SectionResponseDTO.getAllSectionsDTO>> fetchSections() {
        return ApiResponse.of(SuccessStatus.GET_ALL_SECTIONS, SectionConverter.toGetAllSectionsDTO(sectionService.getAllSections()));
    }

    @GetMapping("/select/sections")
    @Operation(summary = "유저가 선택한 section 목록 가져오기")
    public ApiResponse<List<SectionResponseDTO.getAllSectionsDTO>> getUserSections() {
        return ApiResponse.of(SuccessStatus.GET_ALL_SECTIONS, SectionConverter.toGetAllSectionsDTO(userService.getUserSections()));
    }

    @GetMapping("/auth/roles")
    @Operation(summary = "유저의 권한 가져오기")
    public ApiResponse<UserResponseDTO.getUserAuthResultDTO> getUserAuth() {
        return ApiResponse.of(SuccessStatus.GET_ROLE_INFO, UserConverter.toGetUserAuthResultDTO(userService.getUserAuth()));
    }

}
