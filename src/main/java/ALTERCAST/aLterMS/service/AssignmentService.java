package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.apiPayLoad.code.status.ErrorStatus;
import ALTERCAST.aLterMS.apiPayLoad.exception.handler.TempHandler;
import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.repository.AssignmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    @Transactional
    public Assignment getAssignInfo(Long assignId) {

        if (assignmentRepository.findById(assignId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_ASSIGNMENT);
        }

        return assignmentRepository.findById(assignId).get();
    }
}
