package com.example.SF.View;

import com.example.SF.Model.Client;
import com.example.SF.Model.Training;
import com.example.SF.Repository.ITraining;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingService {
    private final ITraining iTraining;
    private final ClientService clientService;

    @Autowired
    public TrainingService(ITraining iTraining, ClientService clientService) {
        this.iTraining = iTraining;
        this.clientService = clientService;
    }

    public List<Training> getAll() {
        try {
            return iTraining.getAll();
        }

        catch (Exception e) {
            System.out.println("Cannot get trainings: " + e.getMessage());
            return List.of();
        }
    }

    public Training getById(UUID id) {
        return iTraining.findById(id).orElse(null);
    }

    public List<Training> getByClient(UUID clientId) {
        try {
            return iTraining.getByClient(clientId);
        }

        catch (Exception e) {
            System.out.println("Cannot find trainings by client ID: " + clientId);
            return List.of();
        }
    }

    public List<Training> getByCategory(UUID clientId, Integer category) {
        try {
            return iTraining.getByCategory(clientId, category);
        }

        catch (Exception e) {
            System.out.println("Cannot find trainings by client ID: " + clientId + " and category ID: " + category);
            return List.of();
        }
    }

    @Transactional
    public Training add(String name, Integer category, UUID clientId) {
        try {
            Training training = new Training();
            training.setTraining_name(name);
            training.setTraining_category(category);

            Client client = clientService.getById(clientId);
            if (client == null) {
                System.out.println("Client not found for ID: " + clientId);
                return null;
            }

            training.setTraining_client(client);
            return iTraining.save(training);
        }

        catch (Exception e) {
            System.out.println("Cannot add training: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void update(UUID id, String name) {
        try {
            iTraining.update(id, name);
        }

        catch (Exception e) {
            System.out.println("Cannot change training's name: " + e.getMessage());
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            iTraining.exclude(id);
        }

        catch (Exception e) {
            System.out.println("Cannot exclude training: " + e.getMessage());
        }
    }
}
