package wagnrd.bookagerserver.account;

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

    @CrossOrigin(exposedHeaders = "X-Auth-Key")
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody User user) {
        System.out.println(user);

        User realUser = userRepository
                .findById(user.getName())
                .orElseThrow(UnauthorizedLoginException::new);

        // TODO: Make login safer
        if (!realUser.getPasswordHash().equals(user.getPasswordHash()))
            throw new UnauthorizedLoginException();

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
