package sf.USF.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "History")
@Table(name = "history", schema = "usf")
@NoArgsConstructor
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JsonProperty("history_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "users", referencedColumnName = "id", nullable = false)
    @JsonProperty("history_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "exercise", referencedColumnName = "id", nullable = false)
    @JsonProperty("history_exercise")
    private Exercise exercise;

    @Column(name = "weight", nullable = true)
    @JsonProperty("history_weight")
    private String weight;

    @Column(name = "reps", nullable = true)
    @JsonProperty("history_reps")
    private String reps;

    @Column(name = "sets", nullable = true)
    @JsonProperty("history_sets")
    private Integer sets;

    @Column(name = "date", nullable = true)
    @JsonProperty("history_date")
    private LocalDate date;
}
