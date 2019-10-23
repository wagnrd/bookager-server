package wagnrd.bookagerserver.data;

import lombok.Data;

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
}
