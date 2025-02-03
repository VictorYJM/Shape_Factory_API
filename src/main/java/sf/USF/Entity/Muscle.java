package sf.USF.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "Muscle")
@Table(name = "muscle", schema = "usf")
@NoArgsConstructor
@Getter
@Setter
public class Muscle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JsonProperty("muscle_id")
    private UUID id;

    @Column(name = "name", nullable = false)
    @JsonProperty("muscle_name")
    private String name;
}
