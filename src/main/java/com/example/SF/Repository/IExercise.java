package com.example.SF.Repository;

import com.example.SF.Model.Exercise;
import com.example.SF.Model.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IExercise extends JpaRepository<Exercise, UUID> {
    @Query(value = "SELECT * FROM V_ExerciseOrder", nativeQuery = true)
    List<Exercise> getAll();

    @Query(value = "SELECT * FROM SF.GET_Exercise(:muscle)", nativeQuery = true)
    List<Exercise> getByMuscle(@Param("muscle") UUID id);

    @Query(value = "SELECT * FROM SF.Exercise WHERE exercise_image = :image", nativeQuery = true)
    List<Exercise> getByImage(@Param("image") String image);

    @Modifying
    @Query("UPDATE Exercise SET exercise_name = :name, exercise_image = :image, exercise_path = :path, exercise_muscle = :muscle WHERE exercise_id = :id")
    void update(
            @Param("id") UUID id,
            @Param("name") String name,
            @Param("image") String image,
            @Param("path") String path,
            @Param("muscle") Muscle muscle
    );
}
