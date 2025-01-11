package com.example.SF.Controll;

import com.example.SF.ImageService;
import com.example.SF.Model.Exercise;
import com.example.SF.View.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/getAll")
    public List<Exercise> getAll() {
        try {
            return exerciseService.getAll();
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @GetMapping("/getByMuscle")
    public List<Exercise> getByMuscle(@RequestParam("muscleId") UUID muscleId) {
        try {
            return exerciseService.getByMuscle(muscleId);
        }

        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public Exercise add(@RequestParam("muscleId") UUID muscleId, @RequestParam("name") String name, @RequestParam("image") MultipartFile image, @RequestParam("path") String path) {
        try {
            String imageUrl = imageService.uploadImage(image);
            return exerciseService.add(name, imageUrl, path, muscleId);
        }

        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestParam("id") UUID id, @RequestParam("muscleId") UUID muscleId, @RequestParam("name") String name, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam("path") String path) {
        try {
            String imageUrl = null;

            if (image != null && !image.isEmpty()) {
                imageUrl = imageService.uploadImage(image);
            }

            exerciseService.update(id, name, imageUrl, path, muscleId);
            return ResponseEntity.ok("Exercises synchronized successfully");
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error synchronizing exercises");
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
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
