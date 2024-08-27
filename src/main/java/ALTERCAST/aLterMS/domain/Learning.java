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
public class Learning extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week_num")
    private String weekNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;

    //private String time;
    // 재생 시간인데, 프론트에서 처리하는건지 잘 모르겠다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    public void setSection(Section section) {
        if (this.section != null) {
            this.section.getLearningList().remove(this);
        }
        this.section = section;
        section.getLearningList().add(this);
    }
}
