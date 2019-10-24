package wagnrd.bookagerserver.bookshelf;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wagnrd.bookagerserver.SessionManager;
import wagnrd.bookagerserver.book.BookNotFoundException;
import wagnrd.bookagerserver.data.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class BookshelfController {
    private final BookshelfRepository bookshelfRepository;
    private final BookRepository bookRepository;
    private final SessionManager sessionManager;
    private final BookshelfBookRelRepository bookshelfBookRelRepository;

    public BookshelfController(BookshelfRepository bookshelfRepository, BookRepository bookRepository, BookshelfBookRelRepository bookshelfBookRelRepository) {
        this.bookshelfRepository = bookshelfRepository;
        this.bookRepository = bookRepository;
        this.bookshelfBookRelRepository = bookshelfBookRelRepository;
        this.sessionManager = SessionManager.getInstance();
    }

    // Bookshelf management
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

        // Delete BookshelfBookRels before deleting the bookshelf
        var relations = bookshelfBookRelRepository.findAll(BookshelfBookRel.bookshelfIdQuery(id));
        bookshelfBookRelRepository.deleteAll(relations);

        bookshelfRepository.delete(bookshelf);

        return ResponseEntity.noContent().build();
    }

    // Book relation management
    @GetMapping("/bookshelves/{id}/books")
    List<Book> books(@RequestHeader(value = "X-Auth-Key") String authKey, @PathVariable Long id) {
        var bookshelf = get(authKey, id);

        var relations = bookshelfBookRelRepository.findAll(BookshelfBookRel.bookshelfIdQuery(id));
        var books = new LinkedList<Book>();
        relations.forEach(relation -> books.add(relation.getBook()));

        return books;
    }

    @PutMapping("/bookshelves/{bookshelfId}/books/{bookId}")
    ResponseEntity<?> addBook(@RequestHeader(value = "X-Auth-Key") String authKey,
                              @PathVariable Long bookshelfId,
                              @PathVariable Long bookId) {
        User user = sessionManager.getSessionUser(authKey);

        // validation of user ownership
        var bookshelf = bookshelfRepository
                .findById(bookshelfId)
                .orElseThrow(BookshelfNotFoundException::new);

        if (!bookshelf.getOwner().equals(user.getName()))
            throw new BookshelfNotFoundException();

        var book = bookRepository
                .findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        if (!book.getOwner().equals(user.getName()))
            throw new BookNotFoundException();

        bookshelfBookRelRepository.save(new BookshelfBookRel(bookshelf, book));

        return ResponseEntity.noContent().build();
    }
}
