package com.example.SF.View;

import com.example.SF.Model.Recipe;
import com.example.SF.Model.Training;
import com.example.SF.Model.Exercise;
import com.example.SF.Repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final TrainingService trainingService;
    private final ExerciseService exerciseService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, TrainingService trainingService, ExerciseService exerciseService) {
        this.recipeRepository = recipeRepository;
        this.trainingService = trainingService;
        this.exerciseService = exerciseService;
    }

    public List<Recipe> getAll() {
        try {
            return recipeRepository.findAll();
        }

        catch (Exception e) {
            return List.of();
        }
    }

    public Recipe getById(UUID id){
        return recipeRepository.findById(id).orElse(null);
    }

    public List<Recipe> getByTraining(UUID trainingId) {
        try {
            return recipeRepository.getByTraining(trainingId);
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

            recipe.setRecipeTraining(training);
            recipe.setRecipeExercise(exercise);
            recipe.setRecipeWeight(weight);
            recipe.setRecipeReps(reps);
            recipe.setRecipeSets(sets);

            return recipeRepository.save(recipe);
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

            List<Recipe> recipes = recipeRepository.getByTraining(trainingId);
            if (recipes.isEmpty()) {
                System.out.println("Cannot find recipes by ID: " + trainingId);
                return;
            }

            Training copied = trainingService.add(training.getTrainingName(), training.getTrainingCategory(), clientId);

            for (Recipe originalRecipe : recipes) {
                Recipe newRecipe = new Recipe();

                newRecipe.setRecipeTraining(copied);
                newRecipe.setRecipeExercise(originalRecipe.getRecipeExercise());
                newRecipe.setRecipeWeight(originalRecipe.getRecipeWeight());
                newRecipe.setRecipeReps(originalRecipe.getRecipeReps());
                newRecipe.setRecipeSets(originalRecipe.getRecipeSets());
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

            for(int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                add(recipe.getRecipeTraining().getTrainingId(), recipe.getRecipeExercise().getExerciseId(), recipe.getRecipeWeight(), recipe.getRecipeReps(), recipe.getRecipeSets());
            }
        }

        catch (Exception e) {
            System.out.println("Cannot delete recipes: " + e.getMessage());
        }
    }
}
