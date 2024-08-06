package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.domain.Learning;
import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.domain.Section;
import ALTERCAST.aLterMS.dto.SectionResponseDTO;

import java.util.List;

public class SectionConverter {
    public static List<SectionResponseDTO.getAllSectionsDTO> toGetAllSectionsDTO(List<Section> sectionList) {
        return sectionList.stream()
                .map(each -> SectionResponseDTO.getAllSectionsDTO.builder()
                        .id(each.getId())
                        .title(each.getCourse().getTitle())
                        .secNum(each.getSecNum())
                        .uploadDay(each.getUploadDay().toString())
                        .year(each.getYear())
                        .semester(each.getSemester().toString())
                        .build())
                .toList();
    }

    public static List<SectionResponseDTO.getSectionNotiDTO> toGetSectionNotiDTO(List<Notification> notifications) {
        return notifications.stream()
                .map(each -> SectionResponseDTO.getSectionNotiDTO.builder()
                        .id(each.getId())
                        .title(each.getTitle())
                        .description(each.getDescription())
                        .build())
                .toList();
    }

    public static List<SectionResponseDTO.getSectionLearningDTO> toGetSectionLearningDTO(List<Learning> learnings) {
        return learnings.stream()
                .map(each -> SectionResponseDTO.getSectionLearningDTO.builder()
                        .id(each.getId())
                        .start(each.getStart().toString())
                        .end(each.getEnd().toString())
                        .weekNum(each.getWeekNum())
                        .build())
                .toList();
    }

    public static List<SectionResponseDTO.getSectionAssignDTO> toGetSectionAssignDTO(List<Assignment> assignments) {
        return assignments.stream()
                .map(each -> SectionResponseDTO.getSectionAssignDTO.builder()
                        .id(each.getId())
                        .title(each.getTitle())
                        .description(each.getDescription())
                        .point(each.getPoint() + "")
                        .deadline(each.getDescription())
                        .build())
                .toList();

    }
}
