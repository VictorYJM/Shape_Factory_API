package sf.USF.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sf.USF.Entity.Recipe;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    @Query(value = "SELECT * FROM SF.GET_Recipe(:training)", nativeQuery = true)
    List<Recipe> findByTraining(@Param("training") UUID training);

    @Modifying
    @Query(value = "DELETE FROM Recipe WHERE training = :training")
    void exclude(@Param("training") UUID training);
}
