package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class FileDownloadController {
    //private final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);
    private final FileService fileService;

    @GetMapping("/files")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileUrl) throws MalformedURLException {
        Resource resource = fileService.downloadFiles(fileUrl);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                //파일 다운로드에 사용되는 contentType(다양한 파일 타입 다룰 때 사용)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileUrl + "\"")
                // attachment => response를 다운로드하여 로컬에 저장하라는 신호
                .body(resource);
    }

}

