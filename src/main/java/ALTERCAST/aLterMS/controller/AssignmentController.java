package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.converter.AssignmentConverter;
import ALTERCAST.aLterMS.converter.NotificationConverter;
import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.dto.AssignmentRequestDTO;
import ALTERCAST.aLterMS.dto.AssignmentResponseDTO;
import ALTERCAST.aLterMS.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PostMapping("/{secId}")
    @Operation(summary = "과제 올리기", description = "과제를 올린다, Instructor만 가능")
    @PreAuthorize("@PrivilegeEvaluator.hasPrivilege(#secId, @Privilege.INSTRUCTOR)")
    public ApiResponse<AssignmentResponseDTO.createAssignResponseDTO> createNotification(@PathVariable(value = "secId") Long secId,
                                                                                         @RequestPart(value = "request", required = false) @Valid AssignmentRequestDTO.createAssignRequestDTO request,
                                                                                         @ModelAttribute(value = "files") @Valid AssignmentRequestDTO.createAssignFileRequestDTO files) throws IOException {
        Assignment assignment = assignmentService.createAssignment(secId, request);
        assignmentService.saveAssignmentFiles(assignment, files);
        return ApiResponse.of(SuccessStatus.CREATE_ASSIGNMENT, AssignmentConverter.toCreateAssignResponseDTO(assignment));
    }
}
