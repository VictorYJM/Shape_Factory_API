package com.example.SF.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "training", schema = "sf")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "training_id", nullable = false)
    @JsonProperty("training_id")
    private UUID trainingId;

    @Column(name = "training_name", nullable = false)
    @JsonProperty("training_name")
    private String trainingName;

    @Column(name = "training_category", nullable = false)
    @JsonProperty("training_category")
    private Integer trainingCategory;

    @ManyToOne
    @JoinColumn(name = "training_client", nullable = false)
    @JsonProperty("training_client")
    private Client trainingClient;
}
