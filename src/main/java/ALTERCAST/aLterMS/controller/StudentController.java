package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.converter.StudentConverter;
import ALTERCAST.aLterMS.domain.Student;
import ALTERCAST.aLterMS.dto.StudentRequestDTO;
import ALTERCAST.aLterMS.dto.StudentResponseDTO;
import ALTERCAST.aLterMS.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    //회원가입
    @PostMapping("/join")
    @Operation(summary = "학생 회원가입", description = "아이디, 이름, Long id 반환")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STUDENT4001", description = "아이디 중복", content = @Content(schema = @Schema(implementation = ApiResponse.class))),

    })
    public ApiResponse<StudentResponseDTO.RegisterResultDTO> join(@Valid @RequestBody StudentRequestDTO.JoinDto request) {
        Student student = studentService.joinStudent(request);
        return ApiResponse.of(SuccessStatus._CREATED, StudentConverter.toJoinResultDTO(student));
    }

    // 로그인은 로그인 필터 통해서 자동으로 권한 확인

    //학생 정보 조회
    @GetMapping("/{stuId}")
    @Operation(summary = "학생 정보 조회", description = "해당 학생 모든 정보 반환, 학생 본인만 확인 가능")
    @Parameter(name = "stuId")
    public ApiResponse<StudentResponseDTO.StuInfoResultDTO> getStuInfo(@PathVariable(value = "stuId") String stuId) {
        Student student = studentService.getInfoOfStudent(stuId);
        return ApiResponse.of(SuccessStatus.GET_STU_INFO, StudentConverter.toStuInfoResultDTO(student));
    }

    //학생 정보 수정
    @PutMapping("/{stuId}")
    @Operation(summary = "학생 정보 수정", description = "수정된 정보 반환, 학생 본인만 가능")
    @Parameter(name = "stuId")
    public ApiResponse<StudentResponseDTO.StuInfoResultDTO> updateStuInfo(@PathVariable(value = "stuId") String stuId, @RequestBody @Valid StudentRequestDTO.UpdateInfoDto request) {
        Student student = studentService.updateInfoOfStudent(stuId, request);
        return ApiResponse.of(SuccessStatus.UPDATE_STU_INFO, StudentConverter.toStuInfoResultDTO(student));
    }

    //학생 탈퇴
    @DeleteMapping("{stuId}")
    @Operation(summary = "학생 탈퇴", description = "Long Id 반환, 학생 본인만 가능")
    @Parameter(name = "stuId")
    public ApiResponse<StudentResponseDTO.RegisterResultDTO> deleteStuInfo(@PathVariable(value = "stuId") String stuId) {
        Student student = studentService.deleteInfoOfStudent(stuId);
        return ApiResponse.of(SuccessStatus.DELETE_STU_INFO, StudentConverter.toJoinResultDTO(student));
    }
}
