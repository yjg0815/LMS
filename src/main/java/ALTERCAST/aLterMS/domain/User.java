package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "`user`")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "user_id")
    private String userId;

    private String password;

    private String email;

    private String phone;

    @Column(name = "dept_name")
    private String deptName;

    public void update(String name, String userId, String password, String email, String phone, String deptName) {
        if (name != null && !name.isBlank())
            this.name = name;
        if (userId != null && !userId.isBlank())
            this.userId = userId;
        if (password != null && !password.isBlank())
            this.password = password;
        if (email != null && !email.isBlank())
            this.email = email;
        if (phone != null && !phone.isBlank())
            this.phone = phone;
        if (deptName != null && !deptName.isBlank())
            this.deptName = deptName;

    }
}
