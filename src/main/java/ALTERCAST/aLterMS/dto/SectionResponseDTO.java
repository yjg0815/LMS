package ALTERCAST.aLterMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SectionResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getAllSectionsDTO {
        Long id;
        String title;
        int secNum;
        String uploadDay;
        String year;
        String semester;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getSectionNotiDTO {
        Long id;
        String title;
        String description;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getSectionAssignDTO {

        Long id;
        String title;
        String description;
        String deadline;
        String point;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getSectionLearningDTO {

        Long id;
        String weekNum;
        String start;
        String end;

    }
}
