package com.example.SF.View;

import com.example.SF.Model.Client;
import com.example.SF.Repository.IClient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    private final IClient iClient;

    @Autowired
    public ClientService(IClient iClient) {
        this.iClient = iClient;
    }

    public List<Client> getAll() {
        try {
            return iClient.getAll();
        }

        catch (Exception e) {
            System.out.println("Cannot get clients: " + e.getMessage());
            return List.of();
        }
    }

    public Client getById(UUID id) {
        return iClient.findById(id).orElse(null);
    }

    public Client getByName(String name) {
        try {
            return iClient.getByName(name);
        }

        catch (Exception e) {
            System.out.println("Cannot find client by name: " + name);
            return null;
        }
    }

    public Client getByEmail(String email) {
        try {
            return iClient.getByEmail(email);
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
            client.setClient_name(name);
            client.setClient_email(email);
            client.setClient_birthday(birthday);
            client.setClient_gender(gender);
            client.setClient_weight(weight);
            client.setClient_password(password);
            client.setClient_active(true);

            return iClient.save(client);
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
            iClient.updateData(id, weight);
        }

        catch (Exception e) {
            System.out.println("Cannot change client's data: " + e.getMessage());
        }
    }

    @Transactional
    public void updatePassword(UUID id, String password) {
        try {
            iClient.updatePassword(id, password);
        }

        catch (Exception e) {
            System.out.println("Cannot change client's password: " + e.getMessage());
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            iClient.exclude(id);
        }

        catch (Exception e) {
            System.out.println("Cannot exclude client: " + e.getMessage());
        }
    }
}