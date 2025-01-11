package com.example.SF.Controll;

import com.example.SF.Model.Exercise;
import com.example.SF.Model.History;
import com.example.SF.Model.Recipe;
import com.example.SF.View.HistoryService;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @CrossOrigin
    @GetMapping("/getAll")
    public List<History> getAll() {
        try {
            return historyService.getAll();
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @GetMapping("/getByClientNoDate")
    public List<History> getByClientNoDate(@RequestParam("clientId") UUID clientId) {
        try {
            return historyService.getByClientNoDate(clientId);
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @GetMapping("/getByClient")
    public List<History> getByClient(@RequestParam("clientId") UUID clientId, @RequestParam("date") String date) {
        try {
            LocalDate dateLocal = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return historyService.getByClient(clientId, dateLocal);
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestParam("clientId") UUID clientId, @RequestBody List<Recipe> recipes) {
        try {
            historyService.add(clientId, recipes);
            return ResponseEntity.ok("Histories added");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot add histories");
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    public ResponseEntity<String> exclude(@RequestParam("id") UUID id) {
        try {
            historyService.exclude(id);
            return ResponseEntity.ok("History excluded");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude history");
        }
    }
}
