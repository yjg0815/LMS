package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
