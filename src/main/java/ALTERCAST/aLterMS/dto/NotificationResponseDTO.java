package ALTERCAST.aLterMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class NotificationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getNotiInfoDTO {
        String userId;
        String title;
        String description;
        String createdAt;
        List<String> fileUrls;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createNotiResponseDTO {
        Long notiId;
    }

}
