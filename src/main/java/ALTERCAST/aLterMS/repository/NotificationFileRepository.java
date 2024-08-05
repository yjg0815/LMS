package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.NotificationFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationFileRepository extends JpaRepository<NotificationFile, Long> {
    List<NotificationFile> findByNotificationId(Long id);

    void deleteByNotificationId(Long id);
}
