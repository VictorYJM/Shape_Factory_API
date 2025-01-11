package com.example.SF.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "history", schema = "sf")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "history_id", nullable = false)
    @JsonProperty("history_id")
    private UUID history_id;

    @ManyToOne
    @JoinColumn(name = "history_client", referencedColumnName = "client_id", nullable = false)
    @JsonProperty("history_client")
    private Client history_client;

    @ManyToOne
    @JoinColumn(name = "history_exercise", referencedColumnName = "exercise_id", nullable = false)
    @JsonProperty("history_exercise")
    private Exercise history_exercise;

    @Column(name = "history_weight", nullable = true)
    @JsonProperty("history_weight")
    private String history_weight;

    @Column(name = "history_reps", nullable = true)
    @JsonProperty("history_reps")
    private String history_reps;

    @Column(name = "history_sets", nullable = true)
    @JsonProperty("history_sets")
    private Integer history_sets;

    @Column(name = "history_date", nullable = true)
    @JsonProperty("history_date")
    private LocalDate history_date;
}