package ALTERCAST.aLterMS.controller;

import ALTERCAST.aLterMS.apiPayLoad.ApiResponse;
import ALTERCAST.aLterMS.apiPayLoad.code.status.SuccessStatus;
import ALTERCAST.aLterMS.converter.SectionConverter;
import ALTERCAST.aLterMS.dto.SectionResponseDTO;
import ALTERCAST.aLterMS.service.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("/{secId}/notifications")
    @Operation(summary = "해당 section의 공지 리스트")
    public ApiResponse<List<SectionResponseDTO.getSectionNotiDTO>> getNotifications(@PathVariable(value = "secId") Long secId) {
        return ApiResponse.of(SuccessStatus.GET_SECTION_NOTIFICATIONS, SectionConverter.toGetSectionNotiDTO(sectionService.getNotifications(secId)));
    }

    @GetMapping("/{secId}/learnings")
    @Operation(summary = "해당 section의 주차 리스트")
    public ApiResponse<List<SectionResponseDTO.getSectionLearningDTO>> getLearnings(@PathVariable(value = "secId") Long secId) {
        return ApiResponse.of(SuccessStatus.GET_SECTION_LEARNINGS, SectionConverter.toGetSectionLearningDTO(sectionService.getLearnings(secId)));
    }

    @GetMapping("/{secId}/assignments")
    @Operation(summary = "해당 section의 과제 리스트")
    public ApiResponse<List<SectionResponseDTO.getSectionAssignDTO>> getAssignments(@PathVariable(value = "secId") Long secId) {
        return ApiResponse.of(SuccessStatus.GET_SECTION_ASSIGNMENTS, SectionConverter.toGetSectionAssignDTO(sectionService.getAssignments(secId)));
    }
}
