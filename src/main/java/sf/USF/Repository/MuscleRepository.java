package sf.USF.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sf.USF.Entity.Muscle;

import java.util.List;
import java.util.UUID;

public interface MuscleRepository extends JpaRepository<Muscle, UUID> {
    @Query(value = "SELECT * FROM V_Muscle", nativeQuery = true)
    List<Muscle> findAllOrder();
}
