package ALTERCAST.aLterMS.domain.role;

import jakarta.persistence.Entity;

@Entity
public class Student extends Role {
    {
        this.allowedPrivileges = Privilege.STUDENT;
    }
}
