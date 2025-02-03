package sf.USF.Service;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.USF.Entity.Exercise;
import sf.USF.Entity.Muscle;
import sf.USF.Repository.ExerciseRepository;
import sf.USF.Repository.MuscleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final MuscleRepository muscleRepository;
    private final ImageService imageService;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository, MuscleRepository muscleRepository, ImageService imageService) {
        this.exerciseRepository = exerciseRepository;
        this.muscleRepository = muscleRepository;
        this.imageService = imageService;
    }

    public List<Exercise> findAll() {
        try {
            return exerciseRepository.findAllOrder();
        }

        catch (Exception e) {
            System.out.println("Cannot find exercises: " + e.getMessage());
            return List.of();
        }
    }

    public List<Exercise> findByMuscle(UUID muscle) {
        try {
            return exerciseRepository.findByMuscle(muscle);
        }

        catch (Exception e) {
            System.out.println("Cannot find exercise by muscle: " + e.getMessage());
            return List.of();
        }
    }

    public List<Exercise> findByImage(String image) {
        try{
            return exerciseRepository.findByImage(image);
        }

        catch (Exception e){
            System.out.println("Cannot find exercises by image: " + e.getMessage());
            return List.of();
        }
    }

    @Transactional
    public Exercise add(String name, String image, String path, UUID muscle) {
        try {
            Exercise exercise = new Exercise();
            exercise.setName(name);
            exercise.setPath(path);

            if (StringUtils.isNotEmpty(image)) {
                exercise.setImage(image);
            }

            Optional<Muscle> muscleDB = muscleRepository.findById(muscle);
            if(muscleDB.isEmpty()) {
                System.out.println("Cannot find muscle by ID: " + muscle);
                return null;
            }

            exercise.setMuscle(muscleDB.get());

            return exerciseRepository.save(exercise);
        }

        catch (Exception e) {
            System.out.println("Cannot add exercise: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void update(UUID id, String name, String image, String path, UUID muscle) {
        try {
            Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
            if (optionalExercise.isPresent()) {
                Exercise exercise = optionalExercise.get();
                String oldImageUrl = exercise.getImage();

                if (StringUtils.isEmpty(image)) {
                    image = oldImageUrl;
                }

                else {
                    List<Exercise> exercisesWithOldImage = exerciseRepository.findByImage(oldImageUrl);
                    if (exercisesWithOldImage.size() == 1) {
                        imageService.deleteImageFromBucket(oldImageUrl);
                    }
                }

                Optional<Muscle> muscleDB = muscleRepository.findById(muscle);
                if(muscleDB.isPresent()) {
                    exerciseRepository.update(id, name, image, path, muscleDB.get());
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
            Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
            if (optionalExercise.isPresent()) {
                Exercise exercise = optionalExercise.get();
                String oldImageUrl = exercise.getImage();

                List<Exercise> exercisesWithOldImage = exerciseRepository.findByImage(oldImageUrl);
                if (exercisesWithOldImage.size() == 1) {
                    imageService.deleteImageFromBucket(oldImageUrl);
                }

                exerciseRepository.deleteById(id);
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
