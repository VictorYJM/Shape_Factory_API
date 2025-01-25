package com.example.SF.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "exercise", schema = "sf")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "exercise_id", nullable = false)
    @JsonProperty("exercise_id")
    private UUID exerciseId;

    @Column(name = "exercise_name", nullable = false)
    @JsonProperty("exercise_name")
    private String exerciseName;

    @Column(name = "exercise_image", nullable = false)
    @JsonProperty("exercise_image")
    private String exerciseImage;

    @Column(name = "exercise_path", nullable = false)
    @JsonProperty("exercise_path")
    private String exercisePath;

    @ManyToOne
    @JoinColumn(name = "exercise_muscle", referencedColumnName = "muscle_id", nullable = false)
    @JsonProperty("exercise_muscle")
    private Muscle exerciseMuscle;
}
