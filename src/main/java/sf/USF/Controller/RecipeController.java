package sf.USF.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sf.USF.Controller.Record.RecipeDTO;
import sf.USF.Entity.Recipe;
import sf.USF.Service.RecipeService;

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
    @GetMapping("/findAll")
    public ResponseEntity<List<Recipe>> findAll() {
        List<Recipe> recipes = recipeService.findAll();
        return recipes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(recipes);
    }

    @CrossOrigin
    @GetMapping("/findByTraining")
    public ResponseEntity<List<Recipe>> findByTraining(@RequestParam("trainingId") UUID training) {
        List<Recipe> recipes = recipeService.findByTraining(training);
        return recipes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(recipes);
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<Recipe> add(@RequestBody RecipeDTO data) {
        Recipe recipe = recipeService.add(data.training(), data.exercise(), data.weight(), data.reps(), data.sets());
        return recipe == null ? ResponseEntity.unprocessableEntity().build() : ResponseEntity.ok(recipe);
    }

    @CrossOrigin
    @PostMapping("/copy")
    public ResponseEntity<String> copy(@RequestParam("userId") UUID user, @RequestParam("trainingId") UUID training) {
        try {
            recipeService.copy(user, training);
            return ResponseEntity.ok("Training copied");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot copy training");
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    public ResponseEntity<String> exclude(@RequestParam("trainingId") UUID training, @RequestBody List<Recipe> recipes) {
        try {
            recipeService.exclude(training, recipes);
            return ResponseEntity.ok("Recipes excluded");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude recipes");
        }
    }
}
