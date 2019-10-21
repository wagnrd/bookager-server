package wagnrd.bookagerserver.login;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wagnrd.bookagerserver.UserRepository;
import wagnrd.bookagerserver.data.User;

@RestController
public class LoginController {
    private final UserRepository userRepository;
    private final SessionManager sessionManager = new SessionManager();

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
