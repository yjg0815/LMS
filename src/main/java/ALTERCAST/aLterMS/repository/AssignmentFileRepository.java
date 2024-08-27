package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.AssignmentFile;
import ALTERCAST.aLterMS.domain.NotificationFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentFileRepository extends JpaRepository<AssignmentFile, Long> {
    List<AssignmentFile> findByAssignmentId(Long id);

    void deleteByAssignmentId(Long id);
}
