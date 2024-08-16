package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.*;
import ALTERCAST.aLterMS.dto.AssignmentRequestDTO;
import ALTERCAST.aLterMS.dto.AssignmentResponseDTO;
import ALTERCAST.aLterMS.dto.NotificationRequestDTO;
import ALTERCAST.aLterMS.dto.NotificationResponseDTO;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AssignmentConverter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static AssignmentResponseDTO.getAssignInfoDTO toGetAssignInfoDTO(Assignment assignment) {
        return AssignmentResponseDTO.getAssignInfoDTO.builder()
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .point(assignment.getPoint() + "")
                .deadline(assignment.getDeadline().toString())
                .fileUrls(assignment.getAssignmentFiles().stream().map(AssignmentFile::getFileUrl).toList())
                .writer(assignment.getWriter())
                .createdAt(assignment.getCreatedAt().toString())
                .updatedAt(assignment.getUpdatedAt().toString())
                .build();
    }

    public static AssignmentResponseDTO.createAssignResponseDTO toCreateAssignResponseDTO(Assignment assignment) {
        return AssignmentResponseDTO.createAssignResponseDTO.builder()
                .assignId(assignment.getId())
                .build();

    }

    public static Assignment toAssignment(AssignmentRequestDTO.createAssignRequestDTO request, Section section, String writer) {
        return Assignment.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .point(Integer.parseInt(request.getPoint()))
                .deadline(LocalDateTime.parse(request.getDeadline(), FORMATTER))
                .section(section)
                .writer(writer)
                .build();

    }

    public static AssignmentFile toAssignmentFile(Assignment assignment, String fileUrls) {
        return AssignmentFile.builder()
                .assignment(assignment)
                .fileUrl(fileUrls)
                .build();
    }
}
