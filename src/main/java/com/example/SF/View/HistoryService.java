package com.example.SF.View;

import com.example.SF.Model.Client;
import com.example.SF.Model.History;
import com.example.SF.Model.Recipe;
import com.example.SF.Repository.HistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final ClientService clientService;

    @Autowired
    public HistoryService(HistoryRepository historyRepository, ClientService clientService) {
        this.historyRepository = historyRepository;
        this.clientService = clientService;
    }

    public List<History> getAll() {
        try {
            return historyRepository.findAll();
        }

        catch (Exception e) {
            System.out.println("Cannot find histories" + e.getMessage());
            return List.of();
        }
    }

    public List<History> getByClientNoDate(UUID clientId) {
        try {
            return historyRepository.getByClientNoDate(clientId);
        }

        catch (Exception e) {
            System.out.println("Cannot find history of client by ID: " + clientId);
            return List.of();
        }
    }

    public List<History> getByClient(UUID clientId, LocalDate date) {
        try {
            return historyRepository.getByClient(clientId, date);
        }

        catch (Exception e) {
            System.out.println("Cannot find history of client by ID: " + clientId);
            return List.of();
        }
    }

    @Transactional
    public void add(UUID clientId, List<Recipe> recipes) {
        try {
            Client client = clientService.getById(clientId);
            if (client == null) {
                System.out.println("Cannot find client by ID: " + clientId);
                return;
            }

            for (Recipe recipe : recipes) {
                if (recipe != null) {
                    History history = new History();
                    history.setHistoryClient(client);
                    history.setHistoryExercise(recipe.getRecipeExercise());
                    history.setHistoryDate(LocalDate.now());
                    history.setHistoryWeight(recipe.getRecipeWeight());
                    history.setHistoryReps(recipe.getRecipeReps());
                    history.setHistorySets(recipe.getRecipeSets());

                    historyRepository.save(history);
                }
            }
        }

        catch (Exception e) {
            System.out.println("Cannot insert histories: " + e.getMessage());
            return;
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