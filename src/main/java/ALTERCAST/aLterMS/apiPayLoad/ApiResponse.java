package ALTERCAST.aLterMS.apiPayLoad;

import ALTERCAST.aLterMS.apiPayLoad.code.BaseCode;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {
    /** API 응답 통일 메뉴얼
     * {
     * 	"isSuccess ": true,  // 성공 여부
     * 	"code" : "2000",  // HTTP 상태코드보다 자세한 응답 상황 (권한이 없으면, 왜 없는지에 대한 구체 상황..)
     * 	"message" : "OK",  // 해당 내용 알기 쉽게
     * 	"result" : // 실제 반환 값 => 제네릭으로
     * 		        {
     * 			"testString" : "This is test!"
     *        }
     * }
     * **/

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;


     // 성공한 경우 응답 생성
    public static <T> ApiResponse<T> onSuccess(T result){
        return new ApiResponse<>(true, SuccessStatus._OK.getCode() , SuccessStatus._OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T result){
            return new ApiResponse<>(true, code.getReasonHttpStatus().getCode() , code.getReasonHttpStatus().getMessage(), result);
    }


    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false, code, message, data);
    }
}
