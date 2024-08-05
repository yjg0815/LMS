package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.apiPayLoad.code.status.ErrorStatus;
import ALTERCAST.aLterMS.apiPayLoad.exception.handler.TempHandler;
import ALTERCAST.aLterMS.converter.NotificationConverter;
import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.domain.NotificationFile;
import ALTERCAST.aLterMS.dto.NotificationRequestDTO;
import ALTERCAST.aLterMS.repository.NotificationFileRepository;
import ALTERCAST.aLterMS.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Getter
public class NotificationFileService {
    private final NotificationRepository notificationRepository;
    private final NotificationFileRepository notificationFileRepository;

    @Transactional
    public void saveFiles(Notification notification, List<MultipartFile> files) throws IOException {
        Path currentPath = Paths.get("").toAbsolutePath();  // 현재 작업 절대경로
        Path saveFilesPath = currentPath.resolve("saveFiles"); // 현재 경로에 saveFiles 경로 추가

        if (!Files.exists(saveFilesPath)) { // 해당 폴더 없으면
            Files.createDirectories(saveFilesPath); // 생성
        }

        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) { // 파일이 있을 때만 image 객체 생성
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename(); // 파일 이름 : 고유식별번호 + 원래 이름

                    Path filePath = saveFilesPath.resolve(fileName); // 파일 경로 : 해당 폴더 + 파일 이름
                    System.out.println(fileName);

                    file.transferTo(filePath.toFile()); // 파일 경로 => 파일 변환 후 해당 경로에 파일 저장

                    NotificationFile notificationFile = NotificationConverter.toNotificationFile(notification, "/saveFiles" + fileName);
                    //notificationFile.setNotification(notification);

                    notificationFileRepository.save(notificationFile);
                }
            }
        }
    }

    @Transactional
    public void deleteFiles(Long notiId) {

        List<NotificationFile> notificationFiles = notificationFileRepository.findByNotificationId(notiId);

        for (NotificationFile notificationFile : notificationFiles) {
            Path currentPath = Paths.get("").toAbsolutePath();  // 현재 작업 절대경로
            String fileUrl = currentPath + notificationFile.getFileUrl(); //현재 작업 절대 경로에 db에 저장되있던 url 추가
            File deleteFile = new File(fileUrl); // 해당 공지에 달린 파일 경로 추출
            deleteFile.delete(); // 로컬 파일 삭제 => boolean 타입이 반환되는데, 에러 메세지 돌려줄지 말지 결정.
        }

        notificationFileRepository.deleteByNotificationId(notiId);
    }
}
