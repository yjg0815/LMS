package ALTERCAST.aLterMS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class NotificationRequestDTO {

    @Getter
    public static class createNotiRequestDTO {

        @NotBlank
        String title;

        @NotBlank
        String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class createNotiFileRequestDTO {
        List<MultipartFile> files;
    }
}
