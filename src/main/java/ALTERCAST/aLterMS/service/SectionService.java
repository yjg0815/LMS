package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.domain.Section;
import ALTERCAST.aLterMS.repository.SectionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepository;

    @Transactional
    public List<Section> getAllSections(){
        return sectionRepository.findAll();
    }
}
