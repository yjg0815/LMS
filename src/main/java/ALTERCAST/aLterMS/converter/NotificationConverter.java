package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.domain.Section;
import ALTERCAST.aLterMS.dto.NotificationRequestDTO;
import ALTERCAST.aLterMS.dto.NotificationResponseDTO;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class NotificationConverter {

    public static NotificationResponseDTO.getNotiInfoDTO toGetNotiInfoDTO(Notification notification) {
        return NotificationResponseDTO.getNotiInfoDTO.builder()
                .title(notification.getTitle())
                .description(notification.getDescription())
                .createdAt(notification.getCreatedAt().toString())
                .userId(SecurityContextHolder.getContext().getAuthentication().getName())
                .build();
    }

    public static NotificationResponseDTO.createNotiResponseDTO toCreateNotiResponseDTO(Notification notification) {
        return NotificationResponseDTO.createNotiResponseDTO.builder()
                .notiId(notification.getId())
                .build();

    }

    public static Notification toNotification(NotificationRequestDTO.createNotiRequestDTO request, Section section) {
        return Notification.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .section(section)
                .build();

    }
}
