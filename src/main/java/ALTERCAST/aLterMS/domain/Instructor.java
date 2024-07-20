package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Instructor extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "inst_id")
    private String instId;

    @Column(name = "user_id")
    private String userId;

    private String password;

    private String email;

    private String phone;

    @Column(name = "dept_name")
    private String deptName;
}
