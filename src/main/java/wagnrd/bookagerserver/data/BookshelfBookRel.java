package wagnrd.bookagerserver.data;

import lombok.Data;
import org.springframework.data.domain.Example;

import javax.persistence.*;

@Entity
@Data
public class BookshelfBookRel {
    @EmbeddedId
    private BookshelfBookId id;

    @ManyToOne
    @MapsId("bookshelfId")
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    public BookshelfBookRel() {}

    public BookshelfBookRel(BookshelfBookId id) {
        this.id = id;
    }

    public BookshelfBookRel(Bookshelf bookshelf, Book book) {
        this.id = new BookshelfBookId(bookshelf.getId(), book.getId());
        this.bookshelf = bookshelf;
        this.book = book;
    }

    public static Example<BookshelfBookRel> bookIdQuery(Long id) {
        return Example.of(new BookshelfBookRel(new BookshelfBookId(null, id)));
    }

    public static Example<BookshelfBookRel> bookshelfIdQuery(Long id) {
        return Example.of(new BookshelfBookRel(new BookshelfBookId(id, null)));
    }
}
