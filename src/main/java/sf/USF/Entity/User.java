package sf.USF.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sf.USF.Controller.Record.LoginRequest;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "User")
@Table(name = "users", schema = "usf")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JsonProperty("user_id")
    private UUID id;

    @Column(name = "email", nullable = false)
    @JsonProperty("user_email")
    private String email;

    @Column(name = "birthday", nullable = false)
    @JsonProperty("user_birthday")
    private LocalDate birthday;

    @Column(name = "gender", nullable = false)
    @JsonProperty("user_gender")
    private Character gender;

    @Column(name = "weight", nullable = false)
    @JsonProperty("user_weight")
    private Double weight;

    @Column(name = "password", nullable = false)
    @JsonProperty("user_password")
    private String password;

    @Column(name = "active", nullable = false)
    @JsonProperty("user_active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    @JsonProperty("user_role")
    private Role role;

    public boolean isLoginCorrect(LoginRequest request, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(request.password(), password);
    }
}
