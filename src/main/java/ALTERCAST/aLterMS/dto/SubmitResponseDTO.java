package ALTERCAST.aLterMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SubmitResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createSubmitResultDTO {
        Long submitId;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getSubmitResultDTO {
        String writer;
        String createdAt;
        String updatedAt;
        String comment;
        List<String> fileUrls;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getAllSubmitResponseDTO {
        Long submitId;
        String writerInfo;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getSubmitInAssignmentDTO {
        Long submitId;
        String userState;
    }
}
