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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/join")
    @Operation(summary = "학생 회원가입", description = "아이디, 이름, Long id 반환")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STUDENT4001", description = "아이디 중복",content = @Content(schema = @Schema(implementation = ApiResponse.class))),

    })
    @Parameter(name = "StudentRequestDTO.JoinDto", description = "회원 가입 요청 객체")
    public ApiResponse<StudentResponseDTO.JoinResultDTO> join(@Valid @RequestBody StudentRequestDTO.JoinDto request) {
        Student student = studentService.joinStudent(request);
        return ApiResponse.of(SuccessStatus._CREATED, StudentConverter.toJoinResultDTO(student));
    }

    // 로그인은 로그인 필터 통해서 자동으로 권한 확인

}
