package onlinebookstore.repository;

import onlinebookstore.entity.Role;
import onlinebookstore.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(RoleName name);
}
