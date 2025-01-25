package com.example.SF.View;

import com.example.SF.Model.Client;
import com.example.SF.Repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAll() {
        try {
            return clientRepository.getAll();
        }

        catch (Exception e) {
            System.out.println("Cannot get clients: " + e.getMessage());
            return List.of();
        }
    }

    public Client getById(UUID id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client getByName(String name) {
        try {
            return clientRepository.getByName(name);
        }

        catch (Exception e) {
            System.out.println("Cannot find client by name: " + name);
            return null;
        }
    }

    public Client getByEmail(String email) {
        try {
            return clientRepository.getByEmail(email);
        }

        catch (Exception e) {
            System.out.println("Cannot find client by email: " + email);
            return null;
        }
    }

    @Transactional
    public Client add(String name, String email, LocalDate birthday, Character gender, Double weight, String password) {
        try {
            Client client = new Client();
            client.setClientName(name);
            client.setClientEmail(email);
            client.setClientBirthday(birthday);
            client.setClientGender(gender);
            client.setClientWeight(weight);
            client.setClientPassword(password);
            client.setClientActive(true);

            return clientRepository.save(client);
        }

        catch (DataIntegrityViolationException e) {
            System.out.println("Email already exists");
            return null;
        }

        catch (Exception e) {
            System.out.println("Cannot add client: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void updateData(UUID id, Double weight) {
        try {
            clientRepository.updateData(id, weight);
        }

        catch (Exception e) {
            System.out.println("Cannot change client's data: " + e.getMessage());
        }
    }

    @Transactional
    public void updatePassword(UUID id, String password) {
        try {
            clientRepository.updatePassword(id, password);
        }

        catch (Exception e) {
            System.out.println("Cannot change client's password: " + e.getMessage());
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            clientRepository.exclude(id);
        }

        catch (Exception e) {
            System.out.println("Cannot exclude client: " + e.getMessage());
        }
    }
}