package sf.USF.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sf.USF.Entity.Muscle;
import sf.USF.Repository.MuscleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class MuscleService {
    private final MuscleRepository muscleRepository;

    @Autowired
    public MuscleService(MuscleRepository muscleRepository) {
        this.muscleRepository = muscleRepository;
    }

    public List<Muscle> findAll() {
        try {
            return muscleRepository.findAllOrder();
        }

        catch (Exception e) {
            System.out.println("Cannot find muscles: " + e.getMessage());
            return List.of();
        }
    }

    @Transactional
    public Muscle add(String name) {
        try {
            Muscle muscle = new Muscle();
            muscle.setName(name);

            return muscleRepository.save(muscle);
        }

        catch (Exception e) {
            System.out.println("Cannot add muscle: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            muscleRepository.deleteById(id);
        }

        catch (Exception e) {
            System.out.println("Cannot exclude muscle: " + e.getMessage());
        }
    }
}
