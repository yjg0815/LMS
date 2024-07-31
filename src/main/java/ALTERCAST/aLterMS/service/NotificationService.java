package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.apiPayLoad.code.status.ErrorStatus;
import ALTERCAST.aLterMS.apiPayLoad.exception.handler.TempHandler;
import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.repository.NotificationRepository;
import ALTERCAST.aLterMS.repository.SectionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional
    public Notification getNotiInfo(Long notiId) {

        if (notificationRepository.findById(notiId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_NOTIFICATION);
        }

        return notificationRepository.findById(notiId).get();
    }
}
