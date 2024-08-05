package ALTERCAST.aLterMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AssignmentResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getAssignInfoDTO {

        String userId;
        String title;
        String description;
        String deadline;
        String point;

    }
}
