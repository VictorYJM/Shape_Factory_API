package com.example.SF.Controll;

import com.example.SF.Model.Muscle;
import com.example.SF.View.MuscleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/getAll")
    public List<Muscle> getAll() {
        try {
            return muscleService.getAll();
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public Muscle add(@RequestParam("name") String name) {
        try {
            return muscleService.add(name);
        }

        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    public ResponseEntity<String> delete(@RequestParam("id") UUID id) {
        try {
            muscleService.exclude(id);
            return ResponseEntity.ok("Muscle excluded");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude muscle");
        }
    }
}
