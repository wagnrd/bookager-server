package wagnrd.bookagerserver.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BookshelfBookId implements Serializable {
    private @Column(name = "bookshelf_id") Long bookshelfId;
    private @Column(name = "book_id") Long bookId;

    public BookshelfBookId() {}

    public BookshelfBookId(Long bookshelfId, Long bookId) {
        this.bookshelfId = bookshelfId;
        this.bookId = bookId;
    }
}
