package sf.USF.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "Exercise")
@Table(name = "exercise", schema = "usf")
@NoArgsConstructor
@Getter
@Setter
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JsonProperty("exercise_id")
    private UUID id;

    @Column(name = "name", nullable = false)
    @JsonProperty("exercise_name")
    private String name;

    @Column(name = "image", nullable = false)
    @JsonProperty("exercise_image")
    private String image;

    @Column(name = "path", nullable = false)
    @JsonProperty("exercise_path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "muscle", referencedColumnName = "id", nullable = false)
    @JsonProperty("exercise_muscle")
    private Muscle muscle;
}
