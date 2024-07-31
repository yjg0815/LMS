package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.domain.Learning;
import ALTERCAST.aLterMS.domain.Notification;
import ALTERCAST.aLterMS.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query("SELECT s.notifications FROM Section s WHERE s.id = :secId")
    List<Notification> findAllNotificationBySecId(@Param("secId") Long secId);

    @Query("SELECT s.learningList FROM Section s WHERE s.id = :secId")
    List<Learning> findAllLearningBySecId(@Param("secId") Long secId);

    @Query("SELECT s.assignments FROM Section s WHERE s.id = :secId")
    List<Assignment> findAllAssignmentBySecId(@Param("secId") Long secId);
}
