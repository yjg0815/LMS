package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String writer;

    @Builder.Default
    @OneToMany(mappedBy = "notification", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<NotificationFile> notificationFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    public void setSection(Section section) {
        if (this.section != null) {
            this.section.getNotifications().remove(this);
        }
        this.section = section;
        section.getNotifications().add(this);
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
