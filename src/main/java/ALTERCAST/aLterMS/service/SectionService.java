package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.apiPayLoad.code.status.ErrorStatus;
import ALTERCAST.aLterMS.apiPayLoad.exception.handler.TempHandler;
import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.domain.Learning;
import ALTERCAST.aLterMS.domain.Notification;
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

    @Transactional
    public List<Notification> getNotifications(Long secId) {
        if (sectionRepository.findById(secId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_SECTION);
        }

        return sectionRepository.findAllNotificationBySecId(secId);
    }

    @Transactional
    public List<Learning> getLearnings(Long secId) {
        if (sectionRepository.findById(secId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_SECTION);
        }

        return sectionRepository.findAllLearningBySecId(secId);
    }

    @Transactional
    public List<Assignment> getAssignments(Long secId) {
        if (sectionRepository.findById(secId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_SECTION);
        }

        return sectionRepository.findAllAssignmentBySecId(secId);
    }
}
