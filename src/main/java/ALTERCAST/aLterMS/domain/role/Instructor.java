package ALTERCAST.aLterMS.domain.role;

import jakarta.persistence.Entity;

@Entity
public class Instructor extends Role {
    {
        this.allowedPrivileges = Privilege.INSTRUCTOR;
    }
}
