package com.example.SF.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "recipe", schema = "sf")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recipe_id", nullable = false)
    @JsonProperty("recipe_id")
    private UUID recipe_id;

    @ManyToOne
    @JoinColumn(name = "recipe_training", referencedColumnName = "training_id", nullable = false)
    @JsonProperty("recipe_training")
    private Training recipe_training;

    @ManyToOne
    @JoinColumn(name = "recipe_exercise", referencedColumnName = "exercise_id", nullable = false)
    @JsonProperty("recipe_exercise")
    private Exercise recipe_exercise;

    @Column(name = "recipe_weight", nullable = true)
    @JsonProperty("recipe_weight")
    private String recipe_weight;

    @Column(name = "recipe_reps", nullable = true)
    @JsonProperty("recipe_reps")
    private String recipe_reps;

    @Column(name = "recipe_sets", nullable = true)
    @JsonProperty("recipe_sets")
    private Integer recipe_sets;
}
