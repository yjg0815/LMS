package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.dto.AssignmentResponseDTO;
import org.springframework.security.core.context.SecurityContextHolder;

public class AssignmentConverter {

    public static AssignmentResponseDTO.getAssignInfoDTO toGetAssignInfoDTO(Assignment assignment) {
        return AssignmentResponseDTO.getAssignInfoDTO.builder()
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .point(assignment.getPoint() + "")
                .deadline(assignment.getDeadline().toString())
                .userId(SecurityContextHolder.getContext().getAuthentication().getName())
                .build();
    }
}
