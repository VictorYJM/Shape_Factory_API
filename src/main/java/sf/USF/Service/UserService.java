package sf.USF.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sf.USF.Entity.Role;
import sf.USF.Entity.User;
import sf.USF.Repository.RoleRepository;
import sf.USF.Repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        try {
            return userRepository.findAllOrder();
        }

        catch (Exception e) {
            System.out.println("Cannot find users: " + e.getMessage());
            return List.of();
        }
    }

    @Transactional
    public User add(String email, String birthday, Character gender, Double weight, String password, String role) {
        try {
            Role userRole = roleRepository.findByName(role);
            Optional<User> userDB = userRepository.findByEmail(email);

            if (userDB.isPresent() || role == null)
                return null;

            User user = new User();
            user.setEmail(email);
            user.setBirthday(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            user.setGender(gender);
            user.setWeight(weight);
            user.setPassword(passwordEncoder.encode(password));
            user.setActive(true);
            user.setRole(userRole);

            return userRepository.save(user);
        }

        catch (Exception e) {
            System.out.println("Cannot add user: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    public void updateData(UUID id, Double weight) {
        try {
            userRepository.updateData(id, weight);
        }

        catch (Exception e) {
            System.out.println("Cannot change user's data: " + e.getMessage());
        }
    }

    @Transactional
    public void updatePassword(UUID id, String password) {
        try {
            userRepository.updatePassword(id, password);
        }

        catch (Exception e) {
            System.out.println("Cannot change user's password: " + e.getMessage());
        }
    }

    @Transactional
    public void exclude(UUID id) {
        try {
            userRepository.exclude(id);
        }

        catch (Exception e) {
            System.out.println("Cannot exclude user: " + e.getMessage());
        }
    }
}
