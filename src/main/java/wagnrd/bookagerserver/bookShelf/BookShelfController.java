package wagnrd.bookagerserver.bookShelf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wagnrd.bookagerserver.BookRepository;
import wagnrd.bookagerserver.BookShelfRepository;
import wagnrd.bookagerserver.UserRepository;
import wagnrd.bookagerserver.account.SessionManager;
import wagnrd.bookagerserver.data.User;

@RestController
public class BookShelfController {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookShelfRepository bookShelfRepository;
    private final SessionManager sessionManager;

    public BookShelfController(UserRepository userRepository, BookRepository bookRepository, BookShelfRepository bookShelfRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookShelfRepository = bookShelfRepository;
        sessionManager = SessionManager.getInstance();
    }

    @GetMapping("/bookShelf")
    ResponseEntity<?> getAllBookShelves(@RequestHeader(value = "X-Auth-Key") String authKey) {
        User user = sessionManager.getSessionUser(authKey);

        return ResponseEntity.ok(user.getBookShelves());
    }

    @GetMapping("/bookShelf/{id}")
    ResponseEntity<?> get(@RequestHeader(value = "X-Auth-Key") String authKey, @PathVariable Long id) {
        sessionManager.validate(authKey);

        return ResponseEntity.ok(bookShelfRepository.findById(id));
    }
}
