package wagnrd.bookagerserver.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wagnrd.bookagerserver.SessionManager;
import wagnrd.bookagerserver.data.*;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@RestController
public class BookController {
    private final BookRepository bookRepository;
    private final BookshelfRepository bookshelfRepository;
    private final BookshelfBookRelRepository bookshelfBookRelRepository;
    private final SessionManager sessionManager;

    public BookController(BookRepository bookRepository, BookshelfRepository bookshelfRepository, BookshelfBookRelRepository bookshelfBookRelRepository) {
        this.bookRepository = bookRepository;
        this.bookshelfRepository = bookshelfRepository;
        this.bookshelfBookRelRepository = bookshelfBookRelRepository;
        this.sessionManager = SessionManager.getInstance();
    }

    /////////////////////
    // Book management //
    /////////////////////

    @GetMapping("/books")
    List<Book> all(@RequestHeader(value = "X-Auth-Key") String authKey) {
        var user = sessionManager.getSessionUser(authKey);
        return bookRepository.findAll(Book.ownerQuery(user.getName()));
    }

    @GetMapping("/books/{id}")
    Book get(@RequestHeader(value = "X-Auth-Key") String authKey, @PathVariable Long id) {
        var user = sessionManager.getSessionUser(authKey);

        var book = bookRepository
                .findById(id)
                .orElseThrow(BookNotFoundException::new);

        // make sure the book belongs to the requesting user
        if (book.getOwner().equals(user.getName()))
            return book;
        else
            throw new BookNotFoundException();
    }

    @PostMapping("/books")
    ResponseEntity<?> add(@RequestHeader(value = "X-Auth-Key") String authKey, @RequestBody Book newBook) {
        var user = sessionManager.getSessionUser(authKey);
        newBook.setOwner(user.getName());     // make sure the real owner cannot be spoofed
        bookRepository.save(newBook);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newBook);
    }

    @PutMapping("/books/{id}")
    ResponseEntity<?> update(@RequestHeader(value = "X-Auth-Key") String authKey, @RequestBody Book newBook, @PathVariable Long id) {
        var book = get(authKey, id);

        book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());
        book.setStatus(newBook.getStatus());
        book.setYear(newBook.getYear());
        book.setLanguage(newBook.getLanguage());
        book.setRating(newBook.getRating());
        book.setDescription(newBook.getDescription());
        book.setComment(newBook.getComment());

        return ResponseEntity.ok(bookRepository.save(book));
    }

    // Also accepts missing attributes in the request body and interprets them as nothing
    // (results in setting the given attribute to null)
    @PutMapping("/books/{id}/{attribute}")
    ResponseEntity<?> updateAttribute(@RequestHeader(value = "X-Auth-Key") String authKey,
                                      @RequestBody Book newBook,
                                      @PathVariable Long id,
                                      @PathVariable String attribute) {
        var book = get(authKey, id);

        try {
            Field field = Book.class.getDeclaredField(attribute);

            if (field.getName().equals("id") || field.getName().equals("owner")) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Attribute \"" + attribute + "\" must not be updated!");
            }

            field.setAccessible(true);
            field.set(book, field.get(newBook));

            return ResponseEntity.ok(bookRepository.save(book));
        } catch (NoSuchFieldException ignored) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Attribute \"" + attribute + "\" not found!");
        } catch (Exception ignored) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("A fatal server error occurred!");
        }
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<?> delete(@RequestHeader(value = "X-Auth-Key") String authKey, @PathVariable Long id) {
        var book = get(authKey, id);

        // Deleting the BookshelfBookRels before deleting the book
        var relations = bookshelfBookRelRepository.findAll(BookshelfBookRel.bookIdQuery(id));
        bookshelfBookRelRepository.deleteAll(relations);

        bookRepository.delete(book);

        return ResponseEntity.noContent().build();
    }

    ///////////////////////////////////
    // Bookshelf relation management //
    ///////////////////////////////////

    @GetMapping("/books/{id}/bookshelves")
    List<Bookshelf> bookshelves(@RequestHeader(value = "X-Auth-Key") String authKey, @PathVariable Long id) {
        var book = get(authKey, id);

        var relations = bookshelfBookRelRepository.findAll(BookshelfBookRel.bookIdQuery(id));
        var bookshelves = new LinkedList<Bookshelf>();
        relations.forEach(relation -> bookshelves.add(relation.getBookshelf()));

        return bookshelves;
    }
}
