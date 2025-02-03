package sf.USF.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "Recipe")
@Table(name = "recipe", schema = "usf")
@NoArgsConstructor
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JsonProperty("recipe_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "training", referencedColumnName = "id", nullable = false)
    @JsonProperty("recipe_training")
    private Training training;

    @ManyToOne
    @JoinColumn(name = "exercise", referencedColumnName = "id", nullable = false)
    @JsonProperty("recipe_exercise")
    private Exercise exercise;

    @Column(name = "weight", nullable = true)
    @JsonProperty("recipe_weight")
    private String weight;

    @Column(name = "reps", nullable = true)
    @JsonProperty("recipe_reps")
    private String reps;

    @Column(name = "sets", nullable = true)
    @JsonProperty("recipe_sets")
    private Integer sets;
}
