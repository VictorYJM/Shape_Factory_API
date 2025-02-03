package sf.USF.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sf.USF.Controller.Record.TrainingDTO;
import sf.USF.Entity.Training;
import sf.USF.Service.TrainingService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/training")
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @CrossOrigin
    @GetMapping("/findAll")
    public ResponseEntity<List<Training>> findAll() {
        List<Training> trainings = trainingService.findAll();
        return trainings.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(trainings);
    }

    @CrossOrigin
    @GetMapping("/findByUser")
    public ResponseEntity<List<Training>> findByUser(@RequestParam("userId") UUID user) {
        List<Training> trainings = trainingService.findByUser(user);
        return trainings.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(trainings);
    }

    @CrossOrigin
    @GetMapping("/findByCategory")
    public ResponseEntity<List<Training>> findByCategory(@RequestParam("userId") UUID user, @RequestParam("category") Integer category) {
        List<Training> trainings = trainingService.findByCategory(user, category);
        return trainings.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(trainings);
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<UUID> add(@RequestBody TrainingDTO data) {
        Training training = trainingService.add(data.name(), data.category(), data.userId());
        return training.getId() == null ? ResponseEntity.unprocessableEntity().build() : ResponseEntity.ok(training.getId());
    }

    @CrossOrigin
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestParam("id") UUID id, @RequestParam("name") String name) {
        try {
            trainingService.update(id, name);
            return ResponseEntity.ok("Training's name changed");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot change training's name");
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    public ResponseEntity<String> exclude(@RequestParam("id") UUID id) {
        try {
            trainingService.exclude(id);
            return ResponseEntity.ok("Training deleted");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude training");
        }
    }
}
