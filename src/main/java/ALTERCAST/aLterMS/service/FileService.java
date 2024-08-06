package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.converter.NotificationConverter;
import ALTERCAST.aLterMS.domain.NotificationFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final Path currentPath = Paths.get("").toAbsolutePath(); // 현재 작업 절대경로
    private String fileName;

    public String saveFiles(MultipartFile file) throws IOException {
        Path saveFilesPath = currentPath.resolve("saveFiles"); // 현재 경로에 saveFiles 경로 추가

        if (!Files.exists(saveFilesPath)) { // 해당 폴더 없으면
            Files.createDirectories(saveFilesPath); // 생성
        }

        if (!file.isEmpty()) { // 파일이 있을 때만 image 객체 생성
            fileName = UUID.randomUUID() + "_" + file.getOriginalFilename(); // 파일 이름 : 고유식별번호 + 원래 이름

            Path filePath = saveFilesPath.resolve(fileName); // 파일 경로 : 해당 폴더 + 파일 이름
            System.out.println(fileName);

            file.transferTo(filePath.toFile()); // 파일 경로 => 파일 변환 후 해당 경로에 파일 저장
        }
        return fileName;
    }

    public void deleteFiles(Long id, String dbFileUrl) {

        String fileUrl = currentPath + dbFileUrl;//현재 작업 절대 경로에 db에 저장되있던 url 추가
        File deleteFile = new File(fileUrl); // 해당 공지에 달린 파일 경로 추출
        deleteFile.delete(); // 로컬 파일 삭제 => boolean 타입이 반환되는데, 에러 메세지 돌려줄지 말지 결정.
    }

    public Resource downloadFiles(String fileUrl) throws MalformedURLException {
        // "saveFiles" 뒤에 슬래시 추가
        fileUrl = fileUrl.replace("/saveFiles", "/saveFiles/");

        // 절대 경로 생성
        Path filePath = Paths.get("").toAbsolutePath().resolve(fileUrl.substring(1)); // 시작 슬래시 제거
        return new UrlResource(filePath.toUri()); // Url로 파일 리소스 찾아서 넘겨주기

    }
}
