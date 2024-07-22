package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Student;
import ALTERCAST.aLterMS.dto.StudentRequestDTO;
import ALTERCAST.aLterMS.dto.StudentResponseDTO;

public class StudentConverter {

    public static StudentResponseDTO.RegisterResultDTO toJoinResultDTO(Student student) {
        return StudentResponseDTO.RegisterResultDTO.builder()
                .Id(student.getId())
                .name(student.getName())
                .stuId(student.getStuId())
                .build();
    }

    public static StudentResponseDTO.StuInfoResultDTO toStuInfoResultDTO(Student student) {
        return StudentResponseDTO.StuInfoResultDTO.builder()
                .Id(student.getId())
                .name(student.getName())
                .stuId(student.getStuId())
                .email(student.getEmail())
                .phone(student.getPhone())
                .deptName(student.getDeptName())
                .password(student.getPassword())
                .build();
    }

    public static Student toStudent(StudentRequestDTO.JoinDto request) {
        return Student.builder()
                .name(request.getName())
                .stuId(request.getStuId())
                .password(request.getPassword())
                .email(request.getEmail())
                .phone(request.getPhone())
                .deptName(request.getDeptName())
                .build();
    }


}
