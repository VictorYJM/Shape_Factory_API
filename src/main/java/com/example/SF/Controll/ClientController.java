package com.example.SF.Controll;

import com.example.SF.Model.Client;
import com.example.SF.View.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @CrossOrigin
    @GetMapping("/getAll")
    public List<Client> getAll() {
        try {
            return clientService.getAll();
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @GetMapping("/getById")
    public Client getById(@RequestParam("id") UUID id) {
        try {
            return clientService.getById(id);
        }

        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin
    @GetMapping("/getByName")
    public Client getByName(@RequestParam("name") String name) {
        try {
            return clientService.getByName(name);
        }

        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin
    @GetMapping("/getByEmail")
    public Client getByEmail(@RequestParam("email") String email) {
        try {
            Client client = clientService.getByEmail(email);
            if (client != null) {
                return client;
            }

            else {
                return null;
            }
        }

        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public UUID add(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("birthday") String birthday, @RequestParam("gender") Character gender, @RequestParam("weight") Double weight, @RequestParam("password") String password) {
        try {
            LocalDate dateBirthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Client client = clientService.add(name, email, dateBirthday, gender, weight, password);
            return client.getClient_id();
        }

        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin
    @PutMapping("/updateData")
    public ResponseEntity<String> updateData(@RequestParam("id") UUID id, @RequestParam("weight") Double weight) {
        try {
            clientService.updateData(id, weight);
            return ResponseEntity.ok("Client's data changed");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot change client's data");
        }
    }

    @CrossOrigin
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestParam("id") UUID id, @RequestParam("password") String password) {
        try {
            clientService.updatePassword(id, password);
            return ResponseEntity.ok("Client's password changed");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot change client's password");
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    public ResponseEntity<String> exclude(@RequestParam("id") UUID id) {
        try {
            clientService.exclude(id);
            return ResponseEntity.ok("Client excluded");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude client");
        }
    }
}
