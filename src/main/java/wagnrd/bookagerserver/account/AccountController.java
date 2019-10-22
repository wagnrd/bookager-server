package wagnrd.bookagerserver.account;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wagnrd.bookagerserver.SessionManager;
import wagnrd.bookagerserver.data.User;
import wagnrd.bookagerserver.data.UserRepository;

@RestController
public class AccountController {
    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    public AccountController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.sessionManager = SessionManager.getInstance();
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody User user) {
        User realUser = userRepository
                .findOne(Example.of(user))
                .orElseThrow(UnauthorizedLoginException::new);

        String authKey = sessionManager.createSession(realUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Auth-Key", authKey)
                .body(realUser);
    }

    @GetMapping("/logout")
    void logout(@RequestHeader(value = "X-Auth-Key") String authKey) {
        sessionManager.delete(authKey);
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody User user) {
        if (!userRepository.existsById(user.getName())) {
            return login(userRepository.save(user));
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }
    }
}
