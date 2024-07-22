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
        if (studentRepository.findByStuId(request.getStuId()).isPresent()) {
            throw new TempHandler(ErrorStatus.ALREADY_EXIST_ID);
            // 이미 중복된 userId 존재
        }
        Student student = StudentConverter.toStudent(request);
        return studentRepository.save(student);
    }

    @Transactional
    public Student getInfoOfStudent(String stuId) {

        if (studentRepository.findByStuId(stuId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_STUDENT);
            // 해당 학생 없음
        }

        return studentRepository.findByStuId(stuId).get();
    }

    @Transactional
    public Student updateInfoOfStudent(String stuId, StudentRequestDTO.UpdateInfoDto request) {
        if (studentRepository.findByStuId(stuId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_STUDENT);
            // 해당 학생 없음
        }
        Student student = studentRepository.findByStuId(stuId).get();
        student.update(request.getName(), request.getStuId(), request.getPassword(),
                request.getEmail(), request.getPhone(), request.getDeptName());

        return studentRepository.save(student);
    }

    @Transactional
    public Student deleteInfoOfStudent(String stuId) {
        if (studentRepository.findByStuId(stuId).isEmpty()) {
            throw new TempHandler(ErrorStatus.NOT_EXIST_STUDENT);
            // 해당 학생 없음
        }
        Student student = studentRepository.findByStuId(stuId).get();
        studentRepository.deleteById(student.getId());

        return student;
    }
}
