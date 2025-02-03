package sf.USF.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sf.USF.Controller.Record.ExerciseDTO;
import sf.USF.Entity.Exercise;
import sf.USF.Service.ExerciseService;
import sf.USF.Service.ImageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;
    private final ImageService imageService;

    public ExerciseController(ExerciseService exerciseService, ImageService imageService) {
        this.exerciseService = exerciseService;
        this.imageService = imageService;
    }

    @CrossOrigin
    @GetMapping("/findAll")
    public ResponseEntity<List<Exercise>> findAll() {
        List<Exercise> exercises = exerciseService.findAll();
        return exercises.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(exercises);
    }

    @CrossOrigin
    @GetMapping("/findByMuscle")
    public ResponseEntity<List<Exercise>> findByMuscle(@RequestParam("muscleId") UUID muscle) {
        List<Exercise> exercises = exerciseService.findByMuscle(muscle);
        return exercises.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(exercises);
    }

    @CrossOrigin
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Exercise> add(@RequestBody ExerciseDTO data, @RequestParam("image") MultipartFile image) {
        String imageUrl = imageService.uploadImage(image);
        Exercise exercise = exerciseService.add(data.name(), imageUrl, data.path(), data.muscle());

        return exercise == null ? ResponseEntity.unprocessableEntity().build() : ResponseEntity.ok(exercise);
    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> update(@RequestParam("id") UUID id, @RequestParam(value = "image", required = false) MultipartFile image, @RequestBody ExerciseDTO data) {
        try {
            String imageUrl = null;

            if (image != null && !image.isEmpty()) {
                imageUrl = imageService.uploadImage(image);
            }

            exerciseService.update(id, data.name(), imageUrl, data.path(), data.muscle());
            return ResponseEntity.ok("Exercises synchronized successfully");
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error synchronizing exercises");
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> exclude(@RequestParam("id") UUID id) {
        try {
            exerciseService.exclude(id);
            return ResponseEntity.ok().body("Exercise excluded");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude exercise");
        }
    }
}
