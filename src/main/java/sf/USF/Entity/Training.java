package sf.USF.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "Training")
@Table(name = "training", schema = "usf")
@NoArgsConstructor
@Getter
@Setter
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JsonProperty("training_id")
    private UUID id;

    @Column(name = "name", nullable = false)
    @JsonProperty("training_name")
    private String name;

    @Column(name = "category", nullable = false)
    @JsonProperty("training_category")
    private Integer category;

    @ManyToOne
    @JoinColumn(name = "users", referencedColumnName = "id", nullable = false)
    @JsonProperty("training_user")
    private User user;
}
