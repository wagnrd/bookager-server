package wagnrd.bookagerserver.bookshelf;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wagnrd.bookagerserver.SessionManager;
import wagnrd.bookagerserver.data.Book;
import wagnrd.bookagerserver.data.BookRepository;
import wagnrd.bookagerserver.data.Bookshelf;
import wagnrd.bookagerserver.data.BookshelfRepository;

import java.util.List;

@RestController
public class BookshelfController {
    private final BookshelfRepository bookshelfRepository;
    private final BookRepository bookRepository;
    private final SessionManager sessionManager;

    public BookshelfController(BookshelfRepository bookshelfRepository, BookRepository bookRepository) {
        this.bookshelfRepository = bookshelfRepository;
        this.bookRepository = bookRepository;
        this.sessionManager = SessionManager.getInstance();
    }

    @GetMapping("/bookshelves")
    List<Bookshelf> getAll(@RequestHeader(value = "X-Auth-Key") String authKey) {
        var user = sessionManager.getSessionUser(authKey);

        return bookshelfRepository.findAll(Bookshelf.ownerQuery(user.getName()));
    }

    @GetMapping("/bookshelves/{id}")
    Bookshelf get(@RequestHeader(value = "X-Auth-Key") String authKey, @PathVariable Long id) {
        var user = sessionManager.getSessionUser(authKey);

        var bookshelf = bookshelfRepository
                .findById(id)
                .orElseThrow(BookshelfNotFoundException::new);

        // make sure the bookshelf belongs to the requesting user
        if (bookshelf.getOwner().equals(user.getName()))
            return bookshelf;
        else
            throw new BookshelfNotFoundException();
    }

    @PostMapping("/bookshelves")
    ResponseEntity<?> add(@RequestHeader(value = "X-Auth-Key") String authKey, @RequestBody Bookshelf newBookshelf) {
        var user = sessionManager.getSessionUser(authKey);
        newBookshelf.setOwner(user.getName());     // make sure the real owner cannot be spoofed
        bookshelfRepository.save(newBookshelf);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newBookshelf);
    }

    @PutMapping("/bookshelves/{id}")
    Bookshelf update(@RequestHeader(value = "X-Auth-Key") String authKey, @RequestBody Bookshelf newBookshelf, @PathVariable Long id) {
        var bookshelf = get(authKey, id);
        bookshelf.setName(newBookshelf.getName());

        return bookshelfRepository.save(bookshelf);
    }

    @DeleteMapping("/bookshelves/{id}")
    ResponseEntity<?> delete(@RequestHeader(value = "X-Auth-Key") String authKey, @PathVariable Long id) {
        var bookshelf = get(authKey, id);
        bookshelfRepository.delete(bookshelf);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bookshelves/{id}/books")
    List<Book> books(@RequestHeader(value = "X-Auth-Key") String authKey, @PathVariable Long id) {
        var bookshelf = get(authKey, id);
        return bookRepository.findAll(Book.bookshelfIdQuery(bookshelf.getId()));
    }
}
