package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SubmitFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "submit_id")
    private Submit submit;


    public void setSubmit(Submit submit) {
        if (this.submit != null) {
            this.submit.getSubmitFiles().remove(this);
        }
        this.submit = submit;
        submit.getSubmitFiles().add(this);
    }
}
