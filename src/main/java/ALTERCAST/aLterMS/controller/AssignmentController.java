package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.converter.AssignmentConverter;
import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.dto.AssignmentRequestDTO;
import ALTERCAST.aLterMS.dto.AssignmentResponseDTO;
import ALTERCAST.aLterMS.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PostMapping(value = "/{secId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "과제 올리기", description = "과제를 올린다, Instructor만 가능")
    @PreAuthorize("@PrivilegeEvaluator.hasPrivilege(#secId, @Privilege.INSTRUCTOR)")
    public ApiResponse<AssignmentResponseDTO.createAssignResponseDTO> createAssignment(@PathVariable(value = "secId") Long secId,
                                                                                       @RequestPart(value = "request", required = false) @Valid AssignmentRequestDTO.createAssignRequestDTO request,
                                                                                       @RequestPart(value = "files", required = false) @Valid List<MultipartFile> files) throws IOException {
        Assignment assignment = assignmentService.createAssignment(secId, request);
        assignmentService.saveAssignmentFiles(assignment, files);
        return ApiResponse.of(SuccessStatus.CREATE_ASSIGNMENT, AssignmentConverter.toCreateAssignResponseDTO(assignment));
    }
}
