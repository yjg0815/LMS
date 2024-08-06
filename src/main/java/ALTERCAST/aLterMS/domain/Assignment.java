package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Assignment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    private int point;
    //해당 과제 배점

    private String writer;

    @Builder.Default
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<AssignmentFile> assignmentFiles = new ArrayList<>();

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
