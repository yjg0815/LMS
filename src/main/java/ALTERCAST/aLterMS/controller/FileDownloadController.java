package ALTERCAST.aLterMS.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class FileDownloadController {
    private final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    @GetMapping("/files")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileUrl) {
        try {
            // "saveFiles" 뒤에 슬래시 추가
            if (fileUrl.startsWith("/saveFiles") && !fileUrl.startsWith("/saveFiles/")) {
                fileUrl = fileUrl.replace("/saveFiles", "/saveFiles/");
            }

            // 절대 경로 생성
            Path filePath = Paths.get("").toAbsolutePath().resolve(fileUrl.substring(1)); // 시작 슬래시 제거
            Resource resource = new UrlResource(filePath.toUri());

            // 리소스 존재 및 접근 가능 여부 확인
            if (resource.exists() && resource.isReadable()) {
                // 디버깅을 위한 로그 출력
                System.out.println("Requested file: " + filePath.toString());
                System.out.println("Resource exists: " + resource.exists());
                System.out.println("Resource is readable: " + resource.isReadable());

                // 헤더 설정 및 리소스 반환
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

