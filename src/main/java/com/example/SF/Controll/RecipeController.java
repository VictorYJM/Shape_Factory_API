package com.example.SF.Controll;

import com.example.SF.Model.Recipe;
import com.example.SF.View.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @CrossOrigin
    @GetMapping("/getAll")
    public List<Recipe> getAll() {
        try {
            return recipeService.getAll();
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @GetMapping("/getByTraining")
    public List<Recipe> getByTraining(@RequestParam("trainingId") UUID training) {
        try {
            return recipeService.getByTraining(training);
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public Recipe add(@RequestParam("trainingId") UUID training, @RequestParam("exerciseId") UUID exercise, @RequestParam("weight") String weight, @RequestParam("reps") String reps, @RequestParam("sets") Integer sets) {
        try {
            return recipeService.add(training, exercise, weight, reps, sets);
        }

        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/copy")
    public ResponseEntity<String> copy(@RequestParam("clientId") UUID client, @RequestParam("trainingId") UUID training) {
        try {
            recipeService.copy(client, training);
            return ResponseEntity.ok("Training copied");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot copy training");
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    public ResponseEntity<String> exclude(@RequestParam("trainingId") UUID trainingId, @RequestBody List<Recipe> recipes) {
        try {
            recipeService.exclude(trainingId, recipes);
            return ResponseEntity.ok("Recipes deleted");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude recipes");
        }
    }
}
