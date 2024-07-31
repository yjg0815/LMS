package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.converter.NotificationConverter;
import ALTERCAST.aLterMS.dto.NotificationResponseDTO;
import ALTERCAST.aLterMS.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
