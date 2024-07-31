package ALTERCAST.aLterMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getNotiInfoDTO {
        String title;
        String description;
        String createdAt;
    }

}
