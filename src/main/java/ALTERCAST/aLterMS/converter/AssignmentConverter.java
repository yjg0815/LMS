package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.dto.AssignmentResponseDTO;

public class AssignmentConverter {

    public static AssignmentResponseDTO.getAssignInfoDTO toGetAssignInfoDTO(Assignment assignment) {
        return AssignmentResponseDTO.getAssignInfoDTO.builder()
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .point(assignment.getPoint()+"")
                .deadline(assignment.getDeadline().toString())
                .build();
    }
}
