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
    private UUID exercise_id;

    @Column(name = "exercise_name", nullable = false)
    @JsonProperty("exercise_name")
    private String exercise_name;

    @Column(name = "exercise_image", nullable = false)
    @JsonProperty("exercise_image")
    private String exercise_image;

    @Column(name = "exercise_path", nullable = false)
    @JsonProperty("exercise_path")
    private String exercise_path;

    @ManyToOne
    @JoinColumn(name = "exercise_muscle", referencedColumnName = "muscle_id", nullable = false)
    @JsonProperty("exercise_muscle")
    private Muscle exercise_muscle;
}
