package sf.USF.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sf.USF.Entity.Training;

import java.util.List;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {
    @Query(value = "SELECT * FROM V_Training", nativeQuery = true)
    List<Training> findAllOrder();

    @Query(value = "SELECT * FROM USF.GET_TrainingByUser(:user)", nativeQuery = true)
    List<Training> findByUser(@Param("user") UUID user);

    @Query(value = "SELECT * FROM USF.GET_TrainingByCategory(:user, :category)", nativeQuery = true)
    List<Training> findByCategory(
            @Param("user") UUID user,
            @Param("category") Integer category
    );

    @Modifying
    @Query("UPDATE Training SET name = :name WHERE id = :id")
    void update(
            @Param("id") UUID id,
            @Param("name") String name
    );
}
