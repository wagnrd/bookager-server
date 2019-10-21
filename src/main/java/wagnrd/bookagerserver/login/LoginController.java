package wagnrd.bookagerserver.login;

import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wagnrd.bookagerserver.UserRepository;
import wagnrd.bookagerserver.data.User;

@RestController
public class LoginController {
    private final UserRepository userRepository;
    private final SessionManager sessions = new SessionManager();

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody User user) {

        if (userRepository.exists(Example.of(user)))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
