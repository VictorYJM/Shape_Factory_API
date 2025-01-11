package com.example.SF.View;

import com.example.SF.ImageService;
import com.example.SF.Model.Exercise;
import com.example.SF.Model.Muscle;
import com.example.SF.Repository.IExercise;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class ExerciseService {
    private final IExercise iExercise;
    private final MuscleService muscleService;
    private final ImageService imageService;

    @Autowired
    public ExerciseService(IExercise iExercise, MuscleService muscleService, ImageService imageService) {
        this.iExercise = iExercise;
        this.muscleService = muscleService;
        this.imageService = imageService;
    }

    public List<Exercise> getAll() {
        try {
            return iExercise.getAll();
        }

        catch (Exception e) {
            System.out.println("Cannot get exercises: " + e.getMessage());
            return List.of();
        }
    }

    public Exercise getById(UUID id) {
        return iExercise.findById(id).orElse(null);
    }

    public List<Exercise> getByMuscle(UUID muscleId) {
        try {
            return iExercise.getByMuscle(muscleId);
        }

        catch (Exception e) {
            System.out.println("Cannot find exercise by muscle: " + e.getMessage());
            return List.of();
        }
    }

    public List<Exercise> getByImage(String image) {
        try{
            return iExercise.getByImage(image);
        }

        catch (Exception e){
            System.out.println("Cannot find exercises by image: " + e.getMessage());
            return List.of();
        }
    }

    @Transactional
    public Exercise add(String name, String image, String path, UUID muscleId) {
        try {
            Exercise exercise = new Exercise();
            exercise.setExercise_name(name);
            exercise.setExercise_path(path);

            if (StringUtils.isNotEmpty(image)) {
                exercise.setExercise_image(image);
            }

            Muscle muscle = muscleService.getById(muscleId);
            if (muscle == null) {
                System.out.println("Cannot find muscle by ID: " + muscleId);
                return null;
            }

            exercise.setExercise_muscle(muscle);

            return iExercise.save(exercise);
        }

        catch (Exception e) {
            System.out.println("Cannot add exercise: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void update(UUID id, String name, String image, String path, UUID muscleId) {
        try {
            Optional<Exercise> optionalExercise = iExercise.findById(id);
            if (optionalExercise.isPresent()) {
                Exercise exercise = optionalExercise.get();
                String oldImageUrl = exercise.getExercise_image();

                if (StringUtils.isEmpty(image)) {
                    image = oldImageUrl;
                }

                else {
                    List<Exercise> exercisesWithOldImage = iExercise.getByImage(oldImageUrl);
                    if (exercisesWithOldImage.size() == 1) {
                        imageService.deleteImageFromBucket(oldImageUrl);
                    }
                }

                Muscle muscle = muscleService.getById(muscleId);
                if (muscle != null) {
                    iExercise.update(id, name, image, path, muscle);
                }
            }

            else {
                System.out.println("Cannot find exercise by ID: " + id);
            }
        }

        catch (Exception e) {
            System.out.println("Cannot update exercise's data: " + e.getMessage());
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            Optional<Exercise> optionalExercise = iExercise.findById(id);
            if (optionalExercise.isPresent()) {
                Exercise exercise = optionalExercise.get();
                String oldImageUrl = exercise.getExercise_image();

                List<Exercise> exercisesWithOldImage = iExercise.getByImage(oldImageUrl);
                if (exercisesWithOldImage.size() == 1) {
                    imageService.deleteImageFromBucket(oldImageUrl);
                }

                iExercise.deleteById(id);
            }

            else {
                System.out.println("Cannot find exercise by ID: " + id);
            }
        }

        catch (Exception e) {
            System.out.println("Cannot exclude exercise: " + e.getMessage());
        }
    }
}
