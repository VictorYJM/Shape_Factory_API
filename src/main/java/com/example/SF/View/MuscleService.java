package com.example.SF.View;

import com.example.SF.Model.Muscle;
import com.example.SF.Repository.IMuscle;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MuscleService {
    private final IMuscle iMuscle;

    @Autowired
    public MuscleService(IMuscle iMuscle) {
        this.iMuscle = iMuscle;
    }

    public List<Muscle> getAll() {
        try {
            return iMuscle.getAll();
        }

        catch (Exception e) {
            System.out.println("Cannot get muscles: " + e.getMessage());
            return List.of();
        }
    }

    public Muscle getById(UUID id) {
        return iMuscle.findById(id).orElse(null);
    }

    @Transactional
    public Muscle add(String name) {
        try {
            Muscle muscle = new Muscle();
            muscle.setMuscle_name(name);

            return iMuscle.save(muscle);
        }

        catch (Exception e) {
            System.out.println("Cannot add muscle: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            iMuscle.deleteById(id);
        }

        catch (Exception e) {
            System.out.println("Cannot exclude muscle: " + e.getMessage());
        }
    }
}
