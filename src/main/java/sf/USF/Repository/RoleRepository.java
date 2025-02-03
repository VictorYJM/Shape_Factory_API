package sf.USF.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sf.USF.Entity.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
