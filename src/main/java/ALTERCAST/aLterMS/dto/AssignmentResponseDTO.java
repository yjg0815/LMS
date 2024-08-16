package ALTERCAST.aLterMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AssignmentResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getAssignInfoDTO {

        String writer;
        String title;
        String description;
        String deadline;
        String point;
        String createdAt;
        String updatedAt;
        List<String> fileUrls;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createAssignResponseDTO {
        Long assignId;
    }
}
