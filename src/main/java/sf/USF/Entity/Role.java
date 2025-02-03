package sf.USF.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "Role")
@Table(name = "role", schema = "usf")
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JsonProperty("role_id")
    private UUID id;

    @Column(name = "name", nullable = false)
    @JsonProperty("role_name")
    private String name;
}
