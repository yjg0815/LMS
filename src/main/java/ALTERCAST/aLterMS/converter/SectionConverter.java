package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Section;
import ALTERCAST.aLterMS.dto.SectionResponseDTO;
import ALTERCAST.aLterMS.dto.UserResponseDTO;

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
}
