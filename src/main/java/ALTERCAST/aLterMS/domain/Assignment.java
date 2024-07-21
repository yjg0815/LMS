package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Assignment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    private int point;
    //해당 과제 배점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_id")
    private Section section;

    public void setSection(Section section) {
        if (this.section != null) {
            this.section.getAssignments().remove(this);
        }
        this.section = section;
        section.getAssignments().add(this);
    }
}
