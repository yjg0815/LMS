package ALTERCAST.aLterMS.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Student extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "stu_id")
    private String stuId;

    private String password;

    private String email;

    private String phone;

    @Column(name = "dept_name")
    private String deptName;

    public void update(String name, String stuId, String password, String email, String phone, String deptName) {
        if (name != null && !name.isBlank())
            this.name = name;
        if (stuId != null && !stuId.isBlank())
            this.stuId = stuId;
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
