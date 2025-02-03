package sf.USF.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import sf.USF.Controller.Record.LoginRequest;
import sf.USF.Controller.Record.LoginResponse;
import sf.USF.Repository.UserRepository;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class TokenController {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {
        var user = userRepository.findByEmail(request.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(request, bCryptPasswordEncoder))
            throw new BadCredentialsException("Email or Password invalid");

        var now = Instant.now();
        var expiresIn = 300L;

        var scope = user.get().getRole().getName();

        var claims = JwtClaimsSet.builder()
                .issuer("sf")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn, scope));
    }
}
