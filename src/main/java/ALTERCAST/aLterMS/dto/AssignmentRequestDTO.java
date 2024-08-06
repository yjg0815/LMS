package ALTERCAST.aLterMS.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AssignmentRequestDTO {
    @Getter
    public static class createAssignRequestDTO {
        String title;
        String description;
        String point;
        String deadline;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class createAssignFileRequestDTO {
        List<MultipartFile> files;
    }
}
