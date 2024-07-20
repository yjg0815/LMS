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
public class Takes extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stu_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_id")
    private Section section;

    @Column(name = "tot_cred")
    private int totCred;

    @OneToMany(mappedBy = "takes", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Assignment> assignments = new ArrayList<>();
}
