package ALTERCAST.aLterMS.apiPayLoad.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ReasonDTO {
    private String message;

    private String code;

    private boolean isSuccess;

    private HttpStatus httpStatus;
}
