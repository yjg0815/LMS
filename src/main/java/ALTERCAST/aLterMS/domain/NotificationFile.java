package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "noti_id")
    private Notification notification;


    public void setNotification(Notification notification) {
        if (this.notification != null) {
            this.notification.getNotificationFiles().remove(this);
        }
        this.notification = notification;
        notification.getNotificationFiles().add(this);
    }
}
