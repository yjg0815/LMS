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
public class Submit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private int score;
    // 과제 점수

    @Setter
    private boolean state;
    // 제출 여부

    private String comment; // 짧은 코멘트

    private String writer;

    @Builder.Default
    @OneToMany(mappedBy = "submit", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<SubmitFile> submitFiles = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "assign_id")
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    //TODO : 나중에 제출물 한번에 볼 수 있는 기능 생각하면 양방향으로 하는게 편할거 같은데 일단 복잡해서 단방향으로

    public void update(String comment) {
        this.comment = comment;
        // 아무것도 안들어오는거 허용
    }

}
