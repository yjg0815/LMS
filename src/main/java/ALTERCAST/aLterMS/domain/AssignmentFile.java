package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AssignmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "assign_id")
    private Assignment assignment;


    public void setAssignment(Assignment assignment) {
        if (this.assignment != null) {
            this.assignment.getAssignmentFiles().remove(this);
        }
        this.assignment = assignment;
        assignment.getAssignmentFiles().add(this);
    }
}
