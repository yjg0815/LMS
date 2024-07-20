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

    private int score;
    // 받은 점수

    private boolean state;
    // 과제 제출 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "takes_id")
    private Takes takes;

    public void setTakes(Takes takes) {
        if (this.takes != null) {
            takes.getAssignments().remove(this);
        }
        this.takes = takes;
        takes.getAssignments().add(this);
    }
}
