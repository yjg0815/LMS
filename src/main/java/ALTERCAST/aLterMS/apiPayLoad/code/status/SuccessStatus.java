package ALTERCAST.aLterMS.apiPayLoad.code.status;

import ALTERCAST.aLterMS.apiPayLoad.code.BaseCode;
import ALTERCAST.aLterMS.apiPayLoad.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    // 인증, 인가 관련 응답
    _OK(HttpStatus.OK, "AUTH200", "로그인이 성공하였습니다."),
    _CREATED(HttpStatus.CREATED, "AUTH201", "회원가입이 성공하였습니다."),

    // 게시물 관련 응답
//    POST_CREATED(HttpStatus.CREATED, "POST201", "게시글 작성이 성공하였습니다."),
//    POST_UPDATED(HttpStatus.OK, "POST200", "게시글 수정이 성공하였습니다."),
//    POST_DELETED(HttpStatus.OK, "POST200", "게시글 삭제가 성공하였습니다."),
//    POST_GET_LIST(HttpStatus.OK, "POST200", "게시글 목록 조회가 성공하였습니다."),
//    POST_GET_DETAIL(HttpStatus.OK, "POST200", "단일 게시글 조회가 성공하였습니다."),

    // 학생 요청 관련 응답
    GET_USER_INFO(HttpStatus.OK, "USER2001", "유저 정보 조회 완료"),
    UPDATE_USER_INFO(HttpStatus.OK, "USER2002", "유저 정보 수정 완료"),
    DELETE_USER_INFO(HttpStatus.OK, "USER2003", "유저 탈퇴 완료"),
    GET_ROLE_INFO(HttpStatus.OK, "USER2004", "유저 역할 조회 완료"),

    //userSection
    CREATE_USER_SECTION(HttpStatus.CREATED, "USERSEC2001", "강의 정보 입력 완료"),

    //Section
    GET_ALL_SECTIONS(HttpStatus.OK, "SEC2001", "전체 강의 조회 완료"),
    GET_SECTION_NOTIFICATIONS(HttpStatus.OK, "SEC2002", "해당 강의 공지 조회 완료"),
    GET_SECTION_ASSIGNMENTS(HttpStatus.OK, "SEC2003", "해당 강의 과제 조회 완료"),
    GET_SECTION_LEARNINGS(HttpStatus.OK, "SEC2004", "해당 강의 주차 조회 완료"),


    //Noti
    CREATE_NOTIFICATION(HttpStatus.CREATED, "NOTI2001", "공지 작성 성공"),

    //Assignment
    CREATE_ASSIGNMENT(HttpStatus.CREATED, "ASSIGN2001", "과제 작성 성공"),

    //Submit
    CREATE_SUBMIT(HttpStatus.CREATED, "SUBMIT2001", "제출물 작성 성공"),
    DELETE_SUBMIT(HttpStatus.OK, "SUBMIT2002", "제출물 삭제 성공"),
    GET_SUBMIT(HttpStatus.OK, "SUBMTI2003", "제출물 정보 조회 완료"),
    UPDATE_SUBMIT(HttpStatus.OK, "SUBMIT2004", "제출물 수정 완료")
    ;


    private final HttpStatus httpStatus;

    private final String code;

    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(this.message)
                .code(this.code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(this.message)
                .code(this.code)
                .isSuccess(true)
                .httpStatus(this.httpStatus)
                .build();
    }
}
