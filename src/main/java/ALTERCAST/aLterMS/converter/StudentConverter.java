package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Student;
import ALTERCAST.aLterMS.dto.StudentRequestDTO;
import ALTERCAST.aLterMS.dto.StudentResponseDTO;

public class StudentConverter {

    public static StudentResponseDTO.JoinResultDTO toJoinResultDTO(Student student) {
        return StudentResponseDTO.JoinResultDTO.builder()
                .Id(student.getId())
                .name(student.getName())
                .userId(student.getUserId())
                .build();
    }

    public static Student toStudent(StudentRequestDTO.JoinDto request) {
        return Student.builder()
                .name(request.getName())
                .userId(request.getUserId())
                .password(request.getPassword())
                .email(request.getEmail())
                .phone(request.getPhone())
                .deptName(request.getDeptName())
                .build();
    }


}
