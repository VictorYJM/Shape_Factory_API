package com.example.SF.Repository;

import com.example.SF.Model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ITraining extends JpaRepository<Training, UUID> {
    @Query(value = "SELECT * FROM V_TrainingOrder", nativeQuery = true)
    List<Training> getAll();

    @Query(value = "SELECT * FROM SF.GET_TrainingByClient(:client)", nativeQuery = true)
    List<Training> getByClient(@Param("client") UUID id);

    @Query(value = "SELECT * FROM SF.GET_TrainingByCategory(:client, :category)", nativeQuery = true)
    List<Training> getByCategory(
            @Param("client") UUID client,
            @Param("category") Integer category
    );

    @Modifying
    @Query("UPDATE Training SET training_name = :name WHERE training_id = :id")
    void update(
            @Param("id") UUID id,
            @Param("name") String name
    );

    @Modifying
    @Query("DELETE Training WHERE training_id = :id")
    void exclude(@Param("id") UUID id);
}
