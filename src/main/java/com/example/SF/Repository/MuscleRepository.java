package com.example.SF.Repository;

import com.example.SF.Model.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MuscleRepository extends JpaRepository<Muscle, UUID> {
    @Query(value = "SELECT * FROM V_MuscleOrder", nativeQuery = true)
    List<Muscle> getAll();
}
