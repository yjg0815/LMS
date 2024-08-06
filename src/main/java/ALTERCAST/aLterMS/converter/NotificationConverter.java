package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.domain.NotificationFile;
import ALTERCAST.aLterMS.domain.Section;
import ALTERCAST.aLterMS.dto.NotificationRequestDTO;
import ALTERCAST.aLterMS.dto.NotificationResponseDTO;
import org.springframework.security.core.context.SecurityContextHolder;

public class NotificationConverter {

    public static NotificationResponseDTO.getNotiInfoDTO toGetNotiInfoDTO(Notification notification) {
        return NotificationResponseDTO.getNotiInfoDTO.builder()
                .title(notification.getTitle())
                .description(notification.getDescription())
                .createdAt(notification.getCreatedAt().toString())
                .userId(SecurityContextHolder.getContext().getAuthentication().getName())
                .fileUrls(notification.getNotificationFiles().stream().map(NotificationFile::getFileUrl).toList())
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

    public static NotificationFile toNotificationFile(Notification notification, String fileUrls) {
        return NotificationFile.builder()
                .notification(notification)
                .fileUrl(fileUrls)
                .build();
    }

}
