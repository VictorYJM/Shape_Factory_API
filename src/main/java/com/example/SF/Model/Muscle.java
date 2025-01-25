package com.example.SF.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "muscle", schema = "sf")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Muscle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "muscle_id", nullable = false)
    @JsonProperty("muscle_id")
    private UUID muscleId;

    @Column(name = "muscle_name", nullable = false)
    @JsonProperty("muscle_name")
    private String muscleName;
}
