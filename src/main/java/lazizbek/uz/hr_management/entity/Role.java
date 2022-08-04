package lazizbek.uz.hr_management.entity;

import lazizbek.uz.hr_management.entity.enums.RoleName;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // role's unique id

    @Enumerated(EnumType.STRING)
    private RoleName roleName;


    @Override
    public String getAuthority() {
        return roleName.name();
    }

    public Role(Integer id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
