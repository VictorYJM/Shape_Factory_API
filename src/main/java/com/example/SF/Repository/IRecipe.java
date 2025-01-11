package com.example.SF.Repository;

import com.example.SF.Model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IRecipe extends JpaRepository<Recipe, UUID> {
    @Query(value = "SELECT * FROM SF.GET_Recipe(:training)", nativeQuery = true)
    List<Recipe> getByTraining(@Param("training") UUID training);

    @Modifying
    @Query(value = "DELETE FROM SF.Recipe WHERE recipe_training = :training", nativeQuery = true)
    void exclude(@Param("training") UUID training);
}
