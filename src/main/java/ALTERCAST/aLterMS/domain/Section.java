package ALTERCAST.aLterMS.domain;

import ALTERCAST.aLterMS.domain.enums.Semester;
import ALTERCAST.aLterMS.domain.enums.UploadDay;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Section extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Semester semester;

    @Column(name = "`YEAR`")
    private String year;

    @Enumerated(EnumType.STRING)
    private UploadDay uploadDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "section", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Learning> learningList = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Assignment> assignments = new ArrayList<>();
}
