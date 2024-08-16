package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.converter.NotificationConverter;
import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.dto.NotificationRequestDTO;
import ALTERCAST.aLterMS.dto.NotificationResponseDTO;
import ALTERCAST.aLterMS.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{notiId}")
    @Operation(summary = "공지 정보")
    public ApiResponse<NotificationResponseDTO.getNotiInfoDTO> getNotificationDetail(@PathVariable(value = "notiId") Long notiId) {
        return ApiResponse.of(SuccessStatus.GET_NOTIFICATION, NotificationConverter.toGetNotiInfoDTO(notificationService.getNotiInfo(notiId)));
    }

    @PostMapping(value = "/{secId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "공지 작성", description = "공지를 작성한다, Instructor만 가능")
    @PreAuthorize("@PrivilegeEvaluator.hasPrivilege(#secId, @Privilege.INSTRUCTOR)")
    public ApiResponse<NotificationResponseDTO.createNotiResponseDTO> createNotification(@PathVariable(value = "secId") Long secId,
                                                                                         @RequestPart(value = "request", required = false) @Valid NotificationRequestDTO.createNotiRequestDTO request,
                                                                                         @RequestPart(value = "files", required = false) @Valid List<MultipartFile> files) throws IOException {
        Notification notification = notificationService.createNotification(secId, request);
        notificationService.saveNotificationFiles(notification, files);
        return ApiResponse.of(SuccessStatus.CREATE_NOTIFICATION, NotificationConverter.toCreateNotiResponseDTO(notification));
    }

    @PutMapping(value = "/{notiId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "공지 수정", description = "공지 수정, 작성자만 가능")
    public ApiResponse<NotificationResponseDTO.createNotiResponseDTO> updateNotification(@PathVariable(value = "notiId") Long notiId,
                                                                                         @RequestPart(value = "request", required = false) @Valid NotificationRequestDTO.createNotiRequestDTO request,
                                                                                         @RequestPart(value = "files", required = false) @Valid List<MultipartFile> files) throws IOException {
        Notification notification = notificationService.updateNotification(notiId, request);
        notificationService.updateNotificationFiles(notification, files);
        return ApiResponse.of(SuccessStatus.UPDATE_NOTIFICATION, NotificationConverter.toCreateNotiResponseDTO(notification));

    }

    @DeleteMapping("/{notiId}")
    @Operation(summary = "공지 삭제", description = "공지를 삭제한다, 작성자만 가능")
    public ApiResponse<Void> deleteNotification(@PathVariable(value = "notiId") Long notiId) {
        notificationService.deleteNotification(notiId);
        return ApiResponse.ofNoting(SuccessStatus.DELETE_NOTIFICATION);
    }
}
