package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.apiPayLoad.code.status.ErrorStatus;
import ALTERCAST.aLterMS.apiPayLoad.exception.handler.TempHandler;
import ALTERCAST.aLterMS.converter.NotificationConverter;
import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.domain.NotificationFile;
import ALTERCAST.aLterMS.dto.NotificationRequestDTO;
import ALTERCAST.aLterMS.repository.NotificationFileRepository;
import ALTERCAST.aLterMS.repository.NotificationRepository;
import ALTERCAST.aLterMS.repository.SectionRepository;
import ALTERCAST.aLterMS.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationFileRepository notificationFileRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final FileService fileService;
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
        if (sectionRepository.findById(secId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_SECTION);
        }
        userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userRepository.findByUserId(userId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_USER);
            // 해당 학생 없음
        }
        String writer = userRepository.findByUserId(userId).get().getName();

        Notification notification = NotificationConverter.toNotification(request, sectionRepository.findById(secId).get(),writer);


        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification updateNotification(Long notiId, NotificationRequestDTO.createNotiRequestDTO request) {
        if (notificationRepository.findById(notiId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_NOTIFICATION);
        }

        Notification notification = notificationRepository.findById(notiId).get();

        userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userId.equals(notification.getWriter())) {
            throw new TempHandler(ErrorStatus._FORBIDDEN);
            // 작성자 아님
        }

        notification.update(request.getTitle(), request.getDescription());
        return notification;
    }

    @Transactional
    public void deleteNotification(Long notiId) {
        if (notificationRepository.findById(notiId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_NOTIFICATION);
        }
        Notification notification = notificationRepository.findById(notiId).get();

        userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userId.equals(notification.getWriter())) {
            throw new TempHandler(ErrorStatus._FORBIDDEN);
            // 작성자 아님
        }

        notificationRepository.deleteById(notiId);
    }


    @Transactional
    public void saveNotificationFiles(Notification notification, List<MultipartFile> files) throws IOException {
        if (files != null) {
            for (MultipartFile file : files) {
                String fileName = fileService.saveFiles(file);
                NotificationFile notificationFile = NotificationConverter.toNotificationFile(notification, "/saveFiles" + fileName);

                notificationFileRepository.save(notificationFile);
            }
        }

    }

    // 공지가 삭제 됐을 때, 파일 같이 날리는거
    // todo : 파일 하나 씩 날리는건 update 로직에서 하자
    @Transactional
    public void deleteNotificationFiles(Long notiId) {
        List<NotificationFile> notificationFiles = notificationFileRepository.findByNotificationId(notiId);
        for (NotificationFile file : notificationFiles) {
            fileService.deleteFiles(notiId, file.getFileUrl());
        }
        notificationFileRepository.deleteByNotificationId(notiId);
    }

    @Transactional
    public void updateNotificationFiles(Notification notification, List<MultipartFile> files) throws IOException {
        deleteNotificationFiles(notification.getId());
        saveNotificationFiles(notification, files);
    }
}
