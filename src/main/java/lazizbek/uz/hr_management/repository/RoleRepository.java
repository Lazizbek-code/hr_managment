package lazizbek.uz.hr_management.repository;

import lazizbek.uz.hr_management.entity.Role;
import lazizbek.uz.hr_management.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName); // find role by name
}
