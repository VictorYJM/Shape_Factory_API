package sf.USF.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.USF.Entity.Training;
import sf.USF.Entity.User;
import sf.USF.Repository.TrainingRepository;
import sf.USF.Repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, UserRepository userRepository) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
    }

    public List<Training> findAll() {
        try {
            return trainingRepository.findAllOrder();
        }

        catch (Exception e) {
            System.out.println("Cannot find trainings: " + e.getMessage());
            return List.of();
        }
    }

    public Optional<Training> findById(UUID id) {
        return trainingRepository.findById(id);
    }

    public List<Training> findByUser(UUID user) {
        try {
            return trainingRepository.findByUser(user);
        }

        catch (Exception e) {
            System.out.println("Cannot find trainings by user ID: " + user);
            return List.of();
        }
    }

    public List<Training> findByCategory(UUID user, Integer category) {
        try {
            return trainingRepository.findByCategory(user, category);
        }

        catch (Exception e) {
            System.out.println("Cannot find trainings by user ID: " + user + " and category ID: " + category);
            return List.of();
        }
    }

    @Transactional
    public Training add(String name, Integer category, UUID user) {
        try {
            Training training = new Training();
            training.setName(name);
            training.setCategory(category);

            Optional<User> userDB = userRepository.findById(user);
            if (userDB.isEmpty()) {
                System.out.println("User not found for ID: " + user);
                return null;
            }

            training.setUser(userDB.get());
            return trainingRepository.save(training);
        }

        catch (Exception e) {
            System.out.println("Cannot add training: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void update(UUID id, String name) {
        try {
            trainingRepository.update(id, name);
        }

        catch (Exception e) {
            System.out.println("Cannot change training's name: " + e.getMessage());
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            trainingRepository.deleteById(id);
        }

        catch (Exception e) {
            System.out.println("Cannot exclude training: " + e.getMessage());
        }
    }
}
