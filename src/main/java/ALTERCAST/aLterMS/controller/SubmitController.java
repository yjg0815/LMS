package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.converter.AssignmentConverter;
import ALTERCAST.aLterMS.converter.SubmitConverter;
import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.domain.Submit;
import ALTERCAST.aLterMS.dto.AssignmentResponseDTO;
import ALTERCAST.aLterMS.dto.SubmitRequestDTO;
import ALTERCAST.aLterMS.dto.SubmitResponseDTO;
import ALTERCAST.aLterMS.service.SubmitService;
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
public class SubmitController {
    private final SubmitService submitService;

    @PostMapping(value = "/{secId}/assignments/{assignId}/submits", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "제출물을 올린다.", description = "학생만 가능, 과제 여러번 제출 불가, 파일 여러개 가능")
    @PreAuthorize("@PrivilegeEvaluator.hasPrivilege(#secId, @Privilege.STUDENT)")
    public ApiResponse<SubmitResponseDTO.createSubmitResultDTO> createSubmit(@PathVariable(value = "secId") Long secId,
                                                                             @PathVariable(value = "assignId") Long assignId,
                                                                             @RequestPart(value = "request", required = false) @Valid SubmitRequestDTO.createSubmitRequestDTO request,
                                                                             @RequestPart(value = "files", required = false) @Valid List<MultipartFile> files) throws IOException {
        Submit submit = submitService.createSubmit(assignId, request);
        submitService.saveSubmitFiles(submit, files);
        return ApiResponse.of(SuccessStatus.CREATE_SUBMIT, SubmitConverter.toCreateSubmitResultDTO(submit));

    }

    @GetMapping("assignments/{assignId}/submits/{submitId}")
    @Operation(summary = "제출물 디테일 확인", description = "뭐 올렸는지 내용 확인")
    public ApiResponse<SubmitResponseDTO.getSubmitResultDTO> getSubmitDetail(@PathVariable(value = "assignId") Long assignId,
                                                                             @PathVariable(value = "submitId") Long submitId) {
        Submit submit = submitService.getSubmit(assignId, submitId);
        return ApiResponse.of(SuccessStatus.GET_SUBMIT, SubmitConverter.toGetSubmitReslutDTO(submit));
    }

    @PutMapping(value = "assignments/{assignId}/submits/{submitId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "제출물 수정", description = "본인만 가능")
    public ApiResponse<SubmitResponseDTO.createSubmitResultDTO> updateSubmit(@PathVariable(value = "assignId") Long assignId,
                                                                             @PathVariable(value = "submitId") Long submitId,
                                                                             @RequestPart(value = "request", required = false) @Valid SubmitRequestDTO.createSubmitRequestDTO request,
                                                                             @RequestPart(value = "files", required = false) @Valid List<MultipartFile> files) throws IOException {
        Submit submit = submitService.updateSubmit(assignId, submitId, request);
        submitService.updateSubmitFiles(submit, files);
        return ApiResponse.of(SuccessStatus.UPDATE_SUBMIT, SubmitConverter.toCreateSubmitResultDTO(submit));

    }

    @DeleteMapping("assignments/{assignId}/submits/{submitId}")
    @Operation(summary = "제출물 파일 삭제", description = "본인만 가능")
    public ApiResponse<Void> deleteSubmit(@PathVariable(value = "assignId") Long assignId,
                                          @PathVariable(value = "submitId") Long submitId) {

        submitService.deleteSubmit(assignId, submitId);
        submitService.deleteSubmitFiles(submitId);
        return ApiResponse.ofNoting(SuccessStatus.DELETE_SUBMIT);
    }

    @GetMapping("{secId}/assignments-all/{assignId}")
    @Operation(summary = "해당 제출물 확인", description = "교수는 학생 모두가 올린거 확인")
    @PreAuthorize("@PrivilegeEvaluator.hasPrivilege(#secId, @Privilege.INSTRUCTOR)")
    public ApiResponse<List<SubmitResponseDTO.getAllSubmitResponseDTO>> getAllSubmit(@PathVariable(value = "secId") Long secId,
                                                                                     @PathVariable(value = "assignId") Long assignId) {
        List<Submit> submitList = submitService.getSubmitList(assignId);
        return ApiResponse.of(SuccessStatus.GET_SUBMIT, SubmitConverter.toGetAllSubmitResponseDTO(submitList));
    }

    @GetMapping("{secId}/assignments/{assignId}")
    @Operation(summary = "해당 제출물 확인", description = "학생은 본인 것만, 과제 하나당 제출은 한 번")
    @PreAuthorize("@PrivilegeEvaluator.hasPrivilege(#secId, @Privilege.STUDENT)")
    public ApiResponse<SubmitResponseDTO.getSubmitInAssignmentDTO> getSubmitInAssignment(@PathVariable(value = "secId") Long secId,
                                                                                         @PathVariable(value = "assignId") Long assignId) {
        Submit submit = submitService.getSubmitInAssignment(assignId);
        return ApiResponse.of(SuccessStatus.GET_SUBMIT, SubmitConverter.toGetSubmitInAssignmentDTO(submit));
    }

    @GetMapping("assignments/{assignId}/submit-check")
    @Operation(summary = "해당 과제 해당 유저의 제출 여부")
    public ApiResponse<SubmitResponseDTO.getHasSubmittedDTO> getHasSubmitted(@PathVariable(value = "assignId") Long assignId) {
        String check = submitService.getHasSubmitted(assignId);
        return ApiResponse.of(SuccessStatus.GET_SUBMIT_STATE, SubmitConverter.toGetHasSubmittedDTO(check));
    }
}
