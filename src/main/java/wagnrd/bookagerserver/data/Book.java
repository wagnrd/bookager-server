package wagnrd.bookagerserver.data;

import lombok.Data;
import org.springframework.data.domain.Example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Book {
    enum ReadingStatus {
        UNREAD,
        READING,
        READ,
        CANCELED
    }

    private @Id @GeneratedValue Long id;
    private String owner;

    private String title;
    private String author;
    private ReadingStatus status;
    private Short year;
    private String language;
    private Byte rating;
    private String description;
    private String comment;

    public Book() {}

    public Book(String title, String author, ReadingStatus status, Short year, String language, Byte rating,
                String description, String comment) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = status;
        this.language = language;
        this.rating = rating;
        this.description = description;
        this.comment = comment;
    }

    public static Example<Book> ownerQuery(String owner) {
        var book = new Book();
        book.owner = owner;

        return Example.of(book);
    }
}
