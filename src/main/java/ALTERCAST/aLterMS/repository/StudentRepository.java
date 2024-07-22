package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStuId(String stuId);
}
