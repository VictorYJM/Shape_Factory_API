package com.example.SF.Controll;

import com.example.SF.Model.Adm;
import com.example.SF.View.AdmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/adm")
public class AdmController {
    private final AdmService admService;

    public AdmController(AdmService admService) {
        this.admService = admService;
    }

    @CrossOrigin
    @GetMapping("/getAll")
    public List<Adm> getAll() {
        try {
            return admService.getAll();
        }

        catch (Exception e) {
            return List.of();
        }
    }

    @CrossOrigin
    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            boolean exists = admService.verify(email, password);
            if (exists) {
                return ResponseEntity.ok("Account verified");
            }

            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot verify adm");
        }
    }

    @CrossOrigin
    @PostMapping("/add")
    public Adm add(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("salary") Double salary) {
        try {
            return admService.add(email, password, salary);
        }

        catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin
    @PutMapping("/updateSalary")
    public ResponseEntity<String> updateSalary(@RequestParam("id") UUID id, @RequestParam("salary") Double salary) {
        try {
            admService.updateSalary(id, salary);
            return ResponseEntity.ok("Adm's salary updated");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Adm's salary cannot be changed");
        }
    }

    @CrossOrigin
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestParam("id") UUID id, @RequestParam("password") String password) {
        try {
            admService.updatePassword(id, password);
            return ResponseEntity.ok("Adm's password updated");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Adm's password cannot be changed");
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    public ResponseEntity<String> exclude(@RequestParam("id") UUID id) {
        try {
            admService.exclude(id);
            return ResponseEntity.ok("Adm excluded");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude adm");
        }
    }
}
