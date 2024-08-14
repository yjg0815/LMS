package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.apiPayLoad.code.status.ErrorStatus;
import ALTERCAST.aLterMS.apiPayLoad.exception.handler.TempHandler;
import ALTERCAST.aLterMS.converter.AssignmentConverter;
import ALTERCAST.aLterMS.converter.NotificationConverter;
import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.domain.AssignmentFile;
import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.domain.NotificationFile;
import ALTERCAST.aLterMS.dto.AssignmentRequestDTO;
import ALTERCAST.aLterMS.repository.AssignmentFileRepository;
import ALTERCAST.aLterMS.repository.AssignmentRepository;
import ALTERCAST.aLterMS.repository.SectionRepository;
import ALTERCAST.aLterMS.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final SectionRepository sectionRepository;
    private final AssignmentFileRepository assignmentFileRepository;
    private final FileService fileService;
    private String userId;
    private final UserRepository userRepository;

    @Transactional
    public Assignment getAssignInfo(Long assignId) {

        if (assignmentRepository.findById(assignId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_ASSIGNMENT);
        }

        return assignmentRepository.findById(assignId).get();
    }

    @Transactional
    public Assignment createAssignment(Long secId, AssignmentRequestDTO.createAssignRequestDTO request) {
        if (sectionRepository.findById(secId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_SECTION);
        }
        userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userRepository.findByUserId(userId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_USER);
            // 해당 학생 없음
        }
        String writer = userRepository.findByUserId(userId).get().getName();
        Assignment assignment = AssignmentConverter.toAssignment(request, sectionRepository.findById(secId).get(), writer);

        return assignmentRepository.save(assignment);
    }

    @Transactional
    public void saveAssignmentFiles(Assignment assignment, List<MultipartFile> files) throws IOException {
//        if (files.getFiles() != null) {
//            for (MultipartFile file : files.getFiles()) {
//                String fileName = fileService.saveFiles(file);
//                AssignmentFile assignmentFile = AssignmentConverter.toAssignmentFile(assignment, "/saveFiles" + fileName);
//
//                assignmentFileRepository.save(assignmentFile);
//            }
//        }
        if (files != null) {
            for (MultipartFile file : files) {
                String fileName = fileService.saveFiles(file);
                AssignmentFile assignmentFile = AssignmentConverter.toAssignmentFile(assignment, "/saveFiles" + fileName);

                assignmentFileRepository.save(assignmentFile);

            }
        }

    }

    // 과제가 삭제 됐을 때, 파일 같이 날리는거
    // todo : 파일 하나 씩 날리는건 update 로직에서 하자
    @Transactional
    public void deleteAssignmentFiles(Long assignId) {
        List<AssignmentFile> assignmentFiles = assignmentFileRepository.findByAssignmentId(assignId);
        for (AssignmentFile file : assignmentFiles) {
            fileService.deleteFiles(assignId, file.getFileUrl());
        }
        assignmentFileRepository.deleteByAssignmentId(assignId);
    }
}
