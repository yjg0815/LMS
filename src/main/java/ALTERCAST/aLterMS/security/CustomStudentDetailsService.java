package ALTERCAST.aLterMS.security;

import ALTERCAST.aLterMS.domain.Student;
import ALTERCAST.aLterMS.repository.StudentRepository;
import ALTERCAST.aLterMS.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserDetailsService 인터페이스 구현 <br>
 * (UserDetailsService : Spring Security 내부에서 현재 사용자 정보 로드할 때 사용하는 인터페이스)
 */
//@Service
//@RequiredArgsConstructor
//public class CustomStudentDetailsService implements UserDetailsService {
//    public final StudentService studentService;
//    public final StudentRepository studentRepository;
//
//    /**
//     * username(=사용자 id)에 해당하는 UserDetails 객체를 반환하는 메소드 <br>
//     * (UserDetails : 사용자 인증 정보를 담음)
//     */
//    @Override
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        System.out.println("CustomUserDetailsService: load username '" + userId + "'");
//        // DB에서 사용자를 로드한다
//        Optional<Student> optionalStudent = studentRepository.findByUserId(userId);
//        if (optionalStudent.isEmpty()) {
//            throw new UsernameNotFoundException("사용자 id를 찾을 수 없습니다 (id=" + userId + ")");
//        }
//        Student student = optionalStudent.get();
//        // CutsomUserDetails에 담아서 반환한다
//        CustomStudentDetails studentDetails = new CustomUserDetails(user, userProjectRepository, userService);
//        return studentDetails;
//    }
//}
