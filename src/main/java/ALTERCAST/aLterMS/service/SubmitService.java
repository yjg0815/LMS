package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.apiPayLoad.code.status.ErrorStatus;
import ALTERCAST.aLterMS.apiPayLoad.exception.handler.TempHandler;
import ALTERCAST.aLterMS.converter.AssignmentConverter;
import ALTERCAST.aLterMS.converter.SubmitConverter;
import ALTERCAST.aLterMS.domain.*;
import ALTERCAST.aLterMS.dto.AssignmentRequestDTO;
import ALTERCAST.aLterMS.dto.SubmitRequestDTO;
import ALTERCAST.aLterMS.repository.AssignmentRepository;
import ALTERCAST.aLterMS.repository.SubmitFileRepository;
import ALTERCAST.aLterMS.repository.SubmitRepository;
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
public class SubmitService {

    private final SubmitRepository submitRepository;
    private final SubmitFileRepository submitFileRepository;
    private final AssignmentRepository assignmentRepository;
    private final FileService fileService;
    private String userId;
    private final UserRepository userRepository;

    @Transactional
    public Submit createSubmit(Long assignId, SubmitRequestDTO.createSubmitRequestDTO request) {
        if (assignmentRepository.findById(assignId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_ASSIGNMENT);
        }
        userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userRepository.findByUserId(userId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_USER);
            // 해당 학생 없음
        }
        Submit submit = SubmitConverter.toSubmit(request, assignmentRepository.findById(assignId).get(),userRepository.findByUserId(userId).get());

        submit.setState(true);
        return submitRepository.save(submit);
    }

    @Transactional
    public Submit getSubmit(Long assignId, Long submitId) {
        if (assignmentRepository.findById(assignId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_ASSIGNMENT);
        }
        if (submitRepository.findById(submitId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_SUBMIT);
        }
        return submitRepository.findById(submitId).get();
    }

    @Transactional
    public Submit updateSubmit(Long assignId, Long submitId, SubmitRequestDTO.createSubmitRequestDTO requestDTO) {
        if (assignmentRepository.findById(assignId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_ASSIGNMENT);
        }
        if (submitRepository.findById(submitId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_SUBMIT);
        }

        Submit submit = submitRepository.findById(submitId).get();

        userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userId.equals(submit.getWriter())) { //작성자가 아닌 경우 권한 예외 발생
            throw new TempHandler(ErrorStatus._FORBIDDEN);
        }

        if (requestDTO != null) {
            submit.update(requestDTO.getComment());
        } else {
            submit.update("");
        }

        return submit;
    }

    @Transactional
    public void deleteSubmit(Long assignId, Long submitId) {
        if (assignmentRepository.findById(assignId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_ASSIGNMENT);
        }
        if (submitRepository.findById(submitId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_SUBMIT);
        }

        submitRepository.deleteById(submitId);
    }

    @Transactional
    public void saveSubmitFiles(Submit submit, List<MultipartFile> files) throws IOException {
        if (files != null) {
            for (MultipartFile file : files) {
                String fileName = fileService.saveFiles(file);
                SubmitFile submitFile = SubmitConverter.toSubmitFile(submit, "/saveFiles" + fileName);

                submitFileRepository.save(submitFile);
            }
        }
    }

    @Transactional
    public void updateSubmitFiles(Submit submit, List<MultipartFile> files) throws IOException {
        deleteSubmitFiles(submit.getId());
        saveSubmitFiles(submit, files);
    }

    // 과제가 삭제 됐을 때, 파일 같이 날리는거
    // todo : 파일 하나 씩 날리는건 update 로직에서 하자 => 아 걍 다날려
    @Transactional
    public void deleteSubmitFiles(Long submitId) {
        List<SubmitFile> submitFiles = submitFileRepository.findBySubmitId(submitId);
        for (SubmitFile file : submitFiles) {
            fileService.deleteFiles(submitId, file.getFileUrl());
        }
        submitFileRepository.deleteBySubmitId(submitId);
    }

    public List<Submit> getSubmitList(Long assignId) {
        if (assignmentRepository.findById(assignId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_ASSIGNMENT);
        }

        userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userId.equals(assignmentRepository.findById(assignId).get().getWriter())) {
            throw new TempHandler(ErrorStatus._FORBIDDEN);
        }

        return submitRepository.findByAssignId(assignId);
    }

    public Submit getSubmitInAssignment(Long assignId) {
        if (assignmentRepository.findById(assignId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_ASSIGNMENT);
        }
        userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Submit submit = submitRepository.findSubmitByAssignIdAndUserId(assignId, userRepository.findByUserId(userId).get().getId());

        if (userId.equals(submitRepository.findById(submit.getId()).get().getWriter())) {
            throw new TempHandler(ErrorStatus._FORBIDDEN);
        }
        return submit;
    }

    public String getHasSubmitted(Long assignId) {
        if (assignmentRepository.findById(assignId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_ASSIGNMENT);
        }

        userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Submit submit = submitRepository.findSubmitByAssignIdAndUserId(assignId, userRepository.findByUserId(userId).get().getId());

        if (submit == null) {
            return "false";
        } else {
            return "true";
        }

    }
}
