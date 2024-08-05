package ALTERCAST.aLterMS.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class NotificationRequestDTO {

    @Getter
    public static class createNotiRequestDTO{
        String title;
        String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class createNotiFileRequestDTO {
        List<MultipartFile> files;
    }
}
