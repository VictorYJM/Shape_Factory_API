package com.example.SF.Controll;

import com.example.SF.Model.Training;
import com.example.SF.View.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/training")
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @CrossOrigin
    @GetMapping("/getAll")
    public List<Training> getAll() {
        try {
            return trainingService.getAll();
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @GetMapping("/getByClient")
    public List<Training> getByClient(@RequestParam("clientId") UUID clientId) {
        try {
            return trainingService.getByClient(clientId);
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @GetMapping("/getByCategory")
    public List<Training> getByCategory(@RequestParam("clientId") UUID clientId, @RequestParam("category") Integer category) {
        try {
            return trainingService.getByCategory(clientId, category);
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public UUID add(@RequestParam("name") String name, @RequestParam("category") Integer category, @RequestParam("clientId") UUID clientId) {
        try {
            Training training = trainingService.add(name, category, clientId);
            return training.getTrainingId();
        }

        catch (Exception e) {
            return null;
        }
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