package sf.USF.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.USF.Entity.Exercise;
import sf.USF.Entity.Recipe;
import sf.USF.Entity.Training;
import sf.USF.Repository.ExerciseRepository;
import sf.USF.Repository.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final TrainingService trainingService;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, TrainingService trainingService, ExerciseRepository exerciseRepository) {
        this.recipeRepository = recipeRepository;
        this.trainingService = trainingService;
        this.exerciseRepository = exerciseRepository;
    }

    public List<Recipe> findAll() {
        try {
            return recipeRepository.findAll();
        }

        catch (Exception e) {
            System.out.println("Cannot find recipes: " + e.getMessage());
            return List.of();
        }
    }

    public List<Recipe> findByTraining(UUID training) {
        try {
            return recipeRepository.findByTraining(training);
        }

        catch (Exception e) {
            System.out.println("Cannot find exercises by training ID: " + training);
            return List.of();
        }
    }

    @Transactional
    public Recipe add(UUID training, UUID exercise, String weight, String reps, Integer sets) {
        try {
            Recipe recipe = new Recipe();

            Optional<Training> trainingDB = trainingService.findById(training);
            if (trainingDB.isEmpty()) {
                System.out.println("Cannot find training by ID: " + training);
                return null;
            }

            Optional<Exercise> exerciseDB = exerciseRepository.findById(exercise);
            if (exerciseDB.isEmpty()) {
                System.out.println("Cannot find exercise by ID: " + exercise);
                return null;
            }

            recipe.setTraining(trainingDB.get());
            recipe.setExercise(exerciseDB.get());
            recipe.setWeight(weight);
            recipe.setReps(reps);
            recipe.setSets(sets);

            return recipeRepository.save(recipe);
        }

        catch (Exception e) {
            System.out.println("Cannot add recipe: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void copy(UUID userId, UUID training) {
        try {
            Optional<Training> trainingDB = trainingService.findById(training);
            if (trainingDB.isEmpty()) {
                System.out.println("Cannot find training by ID: " + training);
                return;
            }

            List<Recipe> recipes = recipeRepository.findByTraining(training);
            if (recipes.isEmpty()) {
                System.out.println("Cannot find recipes by ID: " + training);
                return;
            }

            Training copied = trainingService.add(trainingDB.get().getName(), trainingDB.get().getCategory(), userId);

            for (Recipe originalRecipe : recipes) {
                Recipe newRecipe = new Recipe();

                newRecipe.setTraining(copied);
                newRecipe.setExercise(originalRecipe.getExercise());
                newRecipe.setWeight(originalRecipe.getWeight());
                newRecipe.setReps(originalRecipe.getReps());
                newRecipe.setSets(originalRecipe.getSets());
                recipeRepository.save(newRecipe);
            }
            System.out.println("Recipes copied");
        }

        catch (Exception e) {
            System.out.println("Cannot copy recipes");
        }
    }

    @Transactional
    public void exclude(UUID training, List<Recipe> recipes) {
        try {
            recipeRepository.exclude(training);

            for (int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                add(recipe.getTraining().getId(), recipe.getExercise().getId(), recipe.getWeight(), recipe.getReps(), recipe.getSets());
            }
        }

        catch (Exception e) {
            System.out.println("Cannot exclude recipes: " + e.getMessage());
        }
    }
}
