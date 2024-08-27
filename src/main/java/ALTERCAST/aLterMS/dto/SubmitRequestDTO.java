package ALTERCAST.aLterMS.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class SubmitRequestDTO {

    @Getter
    public static class createSubmitRequestDTO {
        String comment;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class createSubmitFileRequestDTO {
        List<MultipartFile> files;
    }
}
