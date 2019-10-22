package wagnrd.bookagerserver.data;

import lombok.Data;
import org.springframework.data.domain.Example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Book {
    private @Id @GeneratedValue Long id;
    private Long bookshelfId;

    private String title;
    private String author;
    private ReadingStatus status;
    private Short year;
    private Byte rating;
    private String description;
    private String comments;


    public Book() {}

    public Book(String title, String author, ReadingStatus status,
                Short year, Byte rating, String description, String comments) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = status;
        this.rating = rating;
        this.description = description;
        this.comments = comments;
    }

    // Bookshelf id query
    public static Example<Book> boockshelfIdQuery(Long bookshelfId) {
        var book = new Book();
        book.bookshelfId = bookshelfId;

        return Example.of(book);
    }

}
