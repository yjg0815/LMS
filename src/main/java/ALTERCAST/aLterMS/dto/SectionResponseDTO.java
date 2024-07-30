package ALTERCAST.aLterMS.dto;

import ALTERCAST.aLterMS.domain.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
}
