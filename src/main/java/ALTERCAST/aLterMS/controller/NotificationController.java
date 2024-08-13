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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{notiId}")
    @Operation(summary = "공지 정보")
    public ApiResponse<NotificationResponseDTO.getNotiInfoDTO> getNotificationDetail(@PathVariable(value = "notiId") Long notiId) {
        return ApiResponse.of(SuccessStatus.GET_SECTION_NOTIFICATIONS, NotificationConverter.toGetNotiInfoDTO(notificationService.getNotiInfo(notiId)));
    }

    @PostMapping("/{secId}")
    @Operation(summary = "공지 작성", description = "공지를 작성한다, Instructor만 가능")
    @PreAuthorize("@PrivilegeEvaluator.hasPrivilege(#secId, @Privilege.INSTRUCTOR)")
    public ApiResponse<NotificationResponseDTO.createNotiResponseDTO> createNotification(@PathVariable(value = "secId") Long secId,
                                                                                         @RequestPart(value = "request", required = false) @Valid NotificationRequestDTO.createNotiRequestDTO request,
                                                                                         @ModelAttribute(value = "files") @Valid NotificationRequestDTO.createNotiFileRequestDTO files) throws IOException {
        Notification notification = notificationService.createNotification(secId, request);
        notificationService.saveNotificationFiles(notification, files);
        return ApiResponse.of(SuccessStatus.CREATE_NOTIFICATION, NotificationConverter.toCreateNotiResponseDTO(notification));
    }
}
