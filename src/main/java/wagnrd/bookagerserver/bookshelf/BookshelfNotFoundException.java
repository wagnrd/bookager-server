package wagnrd.bookagerserver.bookshelf;

public class BookshelfNotFoundException extends RuntimeException {
    public BookshelfNotFoundException() {
        super("Bookshelf not found!");
    }
}
