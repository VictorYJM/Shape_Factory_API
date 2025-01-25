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
    private UUID historyId;

    @ManyToOne
    @JoinColumn(name = "history_client", referencedColumnName = "client_id", nullable = false)
    @JsonProperty("history_client")
    private Client historyClient;

    @ManyToOne
    @JoinColumn(name = "history_exercise", referencedColumnName = "exercise_id", nullable = false)
    @JsonProperty("history_exercise")
    private Exercise historyExercise;

    @Column(name = "history_weight", nullable = true)
    @JsonProperty("history_weight")
    private String historyWeight;

    @Column(name = "history_reps", nullable = true)
    @JsonProperty("history_reps")
    private String historyReps;

    @Column(name = "history_sets", nullable = true)
    @JsonProperty("history_sets")
    private Integer historySets;

    @Column(name = "history_date", nullable = true)
    @JsonProperty("history_date")
    private LocalDate historyDate;
}