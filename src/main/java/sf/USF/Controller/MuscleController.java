package sf.USF.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sf.USF.Entity.Muscle;
import sf.USF.Service.MuscleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/muscle")
public class MuscleController {
    private final MuscleService muscleService;

    public MuscleController(MuscleService muscleService) {
        this.muscleService = muscleService;
    }

    @CrossOrigin
    @GetMapping("/findAll")
    public ResponseEntity<List<Muscle>> findAll() {
        List<Muscle> muscles = muscleService.findAll();
        return muscles.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(muscles);
    }

    @CrossOrigin
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Muscle> add(@RequestParam("name") String name) {
        Muscle muscle = muscleService.add(name);
        return muscle == null ? ResponseEntity.unprocessableEntity().build() : ResponseEntity.ok(muscle);
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> exclude(@RequestParam("id") UUID id) {
        try {
            muscleService.exclude(id);
            return ResponseEntity.ok("Muscle excluded");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude muscle");
        }
    }
}
