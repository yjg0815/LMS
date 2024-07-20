package ALTERCAST.aLterMS.repository;

import ALTERCAST.aLterMS.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
