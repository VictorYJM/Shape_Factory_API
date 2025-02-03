package sf.USF.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sf.USF.Controller.Record.UserDTO;
import sf.USF.Entity.User;
import sf.USF.Service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody UserDTO data, @RequestParam("role") String role) {
        User user = userService.add(data.email(), data.birthday(), data.gender(), data.weight(), data.password(), role);

        return user == null ? ResponseEntity.unprocessableEntity().build() : ResponseEntity.ok(user);
    }

    @CrossOrigin
    @PutMapping("/updateData")
    public ResponseEntity<String> updateData(@RequestParam("id") UUID id, @RequestParam("weight") Double weight) {
        try {
            userService.updateData(id, weight);
            return ResponseEntity.ok("User's data changed");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot change user's data");
        }
    }

    @CrossOrigin
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestParam("id") UUID id, @RequestParam("password") String password) {
        try {
            userService.updatePassword(id, password);
            return ResponseEntity.ok("User's password changed");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot change user's password");
        }
    }

    @CrossOrigin
    @DeleteMapping("/exclude")
    public ResponseEntity<String> exclude(@RequestParam("id") UUID id) {
        try {
            userService.exclude(id);
            return ResponseEntity.ok("User excluded");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot exclude user");
        }
    }
}
