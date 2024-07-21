package ALTERCAST.aLterMS.apiPayLoad.code.status;

import ALTERCAST.aLterMS.apiPayLoad.code.BaseErrorCode;
import ALTERCAST.aLterMS.apiPayLoad.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    /**
     * ENUM(Http, code, message)
     * enum 상수에 추가적으로 멤버를 줘서 데이터 관리 용이하게
     *
     * 1. common 에러는 COMMON000 으로 둔다. <- 잘 안쓰지만 마땅하지 않을 때 사용
     * 2. 관련된 경우마다 code에 명시적으로 표현한다.
     * 	- 예를 들어 멤버 관련이면 MEMBER001 이런 식으로
     * 3. 2번에 이어서 4000번대를 붙인다. 서버측 잘못은 그냥 COMMON 에러의 서버 에러를 쓰면 됨.
     * 	- MEMBER400_1 아니면 MEMBER4001 이런 식으로
     */

    // 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 내부 에러"),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON4001", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON4003", "권한이 없습니다."),

    // 인증/인가 관련 에러
    MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "AUTH4000", "이미 존재하는 회원입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH4004", "존재하지 않는 이메일입니다"),
    MEMBER_FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH4003", "비밀번호가 일치하지 않습니다"),


    // 게시글 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST4004", "해당 게시글을 찾을 수 없습니다."),
    // For test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트"),

    //Student
    NOT_USER_ID(HttpStatus.BAD_REQUEST, "TEST4001", "테스트")
    ;


    private final HttpStatus httpStatus;

    private final String code;

    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(this.message)
                .code(this.code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(this.message)
                .code(this.code)
                .isSuccess(false)
                .httpStatus(this.httpStatus)
                .build();
    }
}