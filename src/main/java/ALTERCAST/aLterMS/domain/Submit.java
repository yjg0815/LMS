package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Submit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    // 과제 점수

    private boolean state;
    // 제출 여부

    @OneToOne
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_section_id")
    private UserSection userSection;
    //TODO : 나중에 제출물 한번에 볼 수 있는 기능 생각하면 양방향으로 하는게 편할거 같은데 일단 복잡해서 단방향으로

}
