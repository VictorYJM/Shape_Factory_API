package sf.USF.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sf.USF.Entity.Exercise;
import sf.USF.Entity.Muscle;

import java.util.List;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    @Query(value = "SELECT * FROM V_Exercise", nativeQuery = true)
    List<Exercise> findAllOrder();

    @Query(value = "SELECT * FROM USF.GET_Exercise(:muscle)", nativeQuery = true)
    List<Exercise> findByMuscle(@Param("muscle") UUID muscle);

    @Query(value = "SELECT * FROM Exercise WHERE image = :image", nativeQuery = true)
    List<Exercise> findByImage(@Param("image") String image);

    @Modifying
    @Query("UPDATE Exercise SET name = :name, image = :image, path = :path, muscle = :muscle WHERE id = :id")
    void update(
            @Param("id") UUID id,
            @Param("name") String name,
            @Param("image") String image,
            @Param("path") String path,
            @Param("muscle") Muscle muscle
    );
}
