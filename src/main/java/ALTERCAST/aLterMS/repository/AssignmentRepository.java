package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.domain.Submit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
