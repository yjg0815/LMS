package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.apiPayLoad.code.status.ErrorStatus;
import ALTERCAST.aLterMS.apiPayLoad.exception.handler.TempHandler;
import ALTERCAST.aLterMS.converter.NotificationConverter;
import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.domain.User;
import ALTERCAST.aLterMS.dto.NotificationRequestDTO;
import ALTERCAST.aLterMS.repository.NotificationRepository;
import ALTERCAST.aLterMS.repository.SectionRepository;
import ALTERCAST.aLterMS.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SectionRepository sectionRepository;
    private String userId;

    @Transactional
    public Notification getNotiInfo(Long notiId) {

        if (notificationRepository.findById(notiId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_NOTIFICATION);
        }

        return notificationRepository.findById(notiId).get();
    }

    @Transactional
    public Notification createNotification(Long secId, NotificationRequestDTO.createNotiRequestDTO request) {
        userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (sectionRepository.findById(secId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_SECTION);
        }
        Notification notification = NotificationConverter.toNotification(request, sectionRepository.findById(secId).get());

        return notificationRepository.save(notification);

    }
}
