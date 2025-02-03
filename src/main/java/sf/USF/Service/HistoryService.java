package sf.USF.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.USF.Entity.History;
import sf.USF.Entity.Recipe;
import sf.USF.Entity.User;
import sf.USF.Repository.HistoryRepository;
import sf.USF.Repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository, UserRepository userRepository) {
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }

    public List<History> findAll() {
        try {
            return historyRepository.findAll();
        }

        catch (Exception e) {
            System.out.println("Cannot find histories" + e.getMessage());
            return List.of();
        }
    }

    public List<History> findByUserNoDate(UUID userId) {
        try {
            return historyRepository.findByUserNoDate(userId);
        }

        catch (Exception e) {
            System.out.println("Cannot find history of user by ID: " + userId);
            return List.of();
        }
    }

    public List<History> findByUser(UUID userId, LocalDate date) {
        try {
            return historyRepository.findByUser(userId, date);
        }

        catch (Exception e) {
            System.out.println("Cannot find history of user by ID: " + userId);
            return List.of();
        }
    }

    @Transactional
    public List<History> add(UUID userId, List<Recipe> recipes) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                System.out.println("Cannot find user by ID: " + userId);
                return List.of();
            }

            List<History> histories = new ArrayList<>();

            for (Recipe recipe : recipes) {
                if (recipe != null) {
                    History history = new History();
                    history.setUser(user.get());
                    history.setExercise(recipe.getExercise());
                    history.setDate(LocalDate.now());
                    history.setWeight(recipe.getWeight());
                    history.setReps(recipe.getReps());
                    history.setSets(recipe.getSets());

                    histories.add(history);
                }
            }

            return historyRepository.saveAll(histories);
        }

        catch (Exception e) {
            System.out.println("Cannot add histories: " + e.getMessage());
            return List.of();
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            historyRepository.deleteById(id);
        }

        catch (Exception e) {
            System.out.println("Cannot exclude history: " + e.getMessage());
        }
    }
}
