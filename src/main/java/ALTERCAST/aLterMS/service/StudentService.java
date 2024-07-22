package ALTERCAST.aLterMS.service;

import ALTERCAST.aLterMS.apiPayLoad.code.status.ErrorStatus;
import ALTERCAST.aLterMS.apiPayLoad.exception.handler.TempHandler;
import ALTERCAST.aLterMS.converter.StudentConverter;
import ALTERCAST.aLterMS.domain.Student;
import ALTERCAST.aLterMS.dto.StudentRequestDTO;
import ALTERCAST.aLterMS.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public Student joinStudent(StudentRequestDTO.JoinDto request) {
        if (studentRepository.findByUserId(request.getUserId()).isPresent()) {
            throw new TempHandler(ErrorStatus.ALREADY_EXIST_ID);
            // 이미 중복된 userId 존재
        }
        Student student = StudentConverter.toStudent(request);
        return studentRepository.save(student);
    }
}
