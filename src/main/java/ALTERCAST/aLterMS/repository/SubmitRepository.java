package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.domain.Submit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubmitRepository extends JpaRepository<Submit, Long> {

    @Query("SELECT s FROM Submit s WHERE s.assignment.id = :assignId")
    List<Submit> findByAssignId(Long assignId);

    @Query("SELECT s FROM Submit s WHERE s.assignment.id = :assignId and s.user.id = :userId")
    Submit findSubmitByAssignIdAndUserId(@Param("assignId") Long assignId, @Param("userId") Long userId);
}
