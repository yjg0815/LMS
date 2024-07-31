package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.dto.NotificationResponseDTO;

public class NotificationConverter {

    public static NotificationResponseDTO.getNotiInfoDTO toGetNotiInfoDTO(Notification notification) {
        return NotificationResponseDTO.getNotiInfoDTO.builder()
                .title(notification.getTitle())
                .description(notification.getDescription())
                .createdAt(notification.getCreatedAt().toString())
                .build();
    }
}
