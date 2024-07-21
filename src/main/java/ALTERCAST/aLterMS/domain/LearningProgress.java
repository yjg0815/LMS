package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LearningProgress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private boolean state;
    // 학습완료 했는지 => 진도체크는 어려워보임

    @OneToOne
    private Learning learning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "takes_id")
    private Takes takes;
}
