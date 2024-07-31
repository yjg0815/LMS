package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.converter.AssignmentConverter;
import ALTERCAST.aLterMS.converter.NotificationConverter;
import ALTERCAST.aLterMS.dto.AssignmentResponseDTO;
import ALTERCAST.aLterMS.dto.NotificationResponseDTO;
import ALTERCAST.aLterMS.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @GetMapping("/{assignId}")
    @Operation(summary = "과제 정보")
    public ApiResponse<AssignmentResponseDTO.getAssignInfoDTO> getAssignmentDetail(@PathVariable(value = "assignId") Long assignId) {
        return ApiResponse.of(SuccessStatus.GET_SECTION_ASSIGNMENTS, AssignmentConverter.toGetAssignInfoDTO(assignmentService.getAssignInfo(assignId)));
    }
}
