package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.dto.AssignmentRequestDTO;
import ALTERCAST.aLterMS.dto.SubmitRequestDTO;
import ALTERCAST.aLterMS.dto.SubmitResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SubmitController {

    @PostMapping("assignments/{assign_id}/submit")
    @Operation(summary = "과제를 올린다.", description = "학생만 가능, 과제 여러번 제출 불가, 파일 여러개 가능")
    @PreAuthorize("@PrivilegeEvaluator.hasPrivilege(#assignId, @Privilege.STUDENT)")
    public ApiResponse<SubmitResponseDTO.createSubmitResultDTO> createSubmit(@PathVariable(value = "assignId") Long assignId,
                                                                             @RequestPart(value = "request", required = false) @Valid SubmitRequestDTO.createSubmitRequestDTO request,
                                                                             @ModelAttribute(value = "files") @Valid SubmitRequestDTO.createSubmitFileRequestDTO files) throws IOException {
        return null;
    }

    @GetMapping("assignments/{assign_id}/submit/{submit_id}")
    @Operation(summary = "과제 디테일 확인", description = "뭐 올렸는지 내용 확인")
    public ApiResponse<SubmitResponseDTO.getSubmitResultDTO> getSubmit(@PathVariable(value = "assignId") Long assignId,
                                                                       @PathVariable(value = "submitId") Long submitId) {
        return null;
    }

    @PutMapping("assignments/{assign_id}/submit/{submit_id}")
    @Operation(summary = "과제 수정", description = "본인만 가능")
    public ApiResponse<SubmitResponseDTO.updateSubmitResultDTO> updateSubmit(@PathVariable(value = "assignId") Long assignId,
                                                                             @PathVariable(value = "submitId") Long submitId,
                                                                             @RequestPart(value = "request", required = false) @Valid SubmitRequestDTO.createSubmitRequestDTO request,
                                                                             @ModelAttribute(value = "files") @Valid SubmitRequestDTO.createSubmitFileRequestDTO files){
        return null;

    }

    @DeleteMapping("assignments/{assign_id}/submit/{submit_id}")
    @Operation(summary = "과제 삭제", description = "본인만 가능")
    public ApiResponse<Void> deleteSubmit(@PathVariable(value = "assignId") Long assignId,
                                          @PathVariable(value = "submitId") Long submitId){
        return ApiResponse.ofNoting(SuccessStatus.DELETE_SUBMIT);
    }

}
