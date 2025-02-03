package sf.USF.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sf.USF.Entity.History;
import sf.USF.Entity.Recipe;
import sf.USF.Service.HistoryService;

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
    @GetMapping("/findAll")
    public ResponseEntity<List<History>> findAll() {
        List<History> histories = historyService.findAll();
        return histories.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(histories);
    }

    @CrossOrigin
    @GetMapping("/findByUserNoDate")
    public ResponseEntity<List<History>> findByUserNoDate(@RequestParam("userId") UUID user) {
        List<History> histories = historyService.findByUserNoDate(user);
        return histories.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(histories);
    }

    @CrossOrigin
    @GetMapping("/findByUser")
    public ResponseEntity<List<History>> findByUser(@RequestParam("userId") UUID user, @RequestParam("date") String date) {
        LocalDate dateLocal = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<History> histories = historyService.findByUser(user, dateLocal);

        return histories.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(histories);
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<List<History>> add(@RequestParam("userId") UUID user, @RequestBody List<Recipe> recipes) {
        List<History> histories = historyService.add(user, recipes);
        return histories.isEmpty() ? ResponseEntity.unprocessableEntity().build() : ResponseEntity.ok(histories);
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
