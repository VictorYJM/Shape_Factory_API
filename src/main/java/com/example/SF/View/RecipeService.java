package com.example.SF.View;

import com.example.SF.Model.Recipe;
import com.example.SF.Model.Training;
import com.example.SF.Model.Exercise;
import com.example.SF.Repository.IRecipe;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class RecipeService {
    private final IRecipe iRecipe;
    private final TrainingService trainingService;
    private final ExerciseService exerciseService;

    @Autowired
    public RecipeService(IRecipe iRecipe, TrainingService trainingService, ExerciseService exerciseService) {
        this.iRecipe = iRecipe;
        this.trainingService = trainingService;
        this.exerciseService = exerciseService;
    }

    public List<Recipe> getAll() {
        try {
            return iRecipe.findAll();
        }

        catch (Exception e) {
            return List.of();
        }
    }

    public Recipe getById(UUID id){
        return iRecipe.findById(id).orElse(null);
    }

    public List<Recipe> getByTraining(UUID trainingId) {
        try {
            return iRecipe.getByTraining(trainingId);
        }

        catch (Exception e) {
            System.out.println("Cannot find exercises by training ID: " + trainingId);
            return List.of();
        }
    }

    @Transactional
    public Recipe add(UUID trainingId, UUID exerciseId, String weight, String reps, Integer sets) {
        try {
            Recipe recipe = new Recipe();

            Training training = trainingService.getById(trainingId);
            if (training == null) {
                System.out.println("Cannot find training by ID: " + trainingId);
                return null;
            }

            Exercise exercise = exerciseService.getById(exerciseId);
            if (exercise == null) {
                System.out.println("Cannot find exercise by ID: " + exerciseId);
                return null;
            }

            recipe.setRecipe_training(training);
            recipe.setRecipe_exercise(exercise);
            recipe.setRecipe_weight(weight);
            recipe.setRecipe_reps(reps);
            recipe.setRecipe_sets(sets);

            return iRecipe.save(recipe);
        }

        catch (Exception e) {
            System.out.println("Cannot insert recipe: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void copy(UUID clientId, UUID trainingId) {
        try {
            Training training = trainingService.getById(trainingId);
            if (training == null) {
                System.out.println("Cannot find training by ID: " + trainingId);
                return;
            }

            List<Recipe> recipes = iRecipe.getByTraining(trainingId);
            if (recipes.isEmpty()) {
                System.out.println("Cannot find recipes by ID: " + trainingId);
                return;
            }

            Training copied = trainingService.add(training.getTraining_name(), training.getTraining_category(), clientId);

            for (Recipe originalRecipe : recipes) {
                Recipe newRecipe = new Recipe();

                newRecipe.setRecipe_training(copied);
                newRecipe.setRecipe_exercise(originalRecipe.getRecipe_exercise());
                newRecipe.setRecipe_weight(originalRecipe.getRecipe_weight());
                newRecipe.setRecipe_reps(originalRecipe.getRecipe_reps());
                newRecipe.setRecipe_sets(originalRecipe.getRecipe_sets());
                iRecipe.save(newRecipe);
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
            iRecipe.exclude(training);

            for(int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                add(recipe.getRecipe_training().getTraining_id(), recipe.getRecipe_exercise().getExercise_id(), recipe.getRecipe_weight(), recipe.getRecipe_reps(), recipe.getRecipe_sets());
            }
        }

        catch (Exception e) {
            System.out.println("Cannot delete recipes: " + e.getMessage());
        }
    }
}
