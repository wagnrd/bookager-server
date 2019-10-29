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
    private String year;
    private String genre;
    private String language;
    private Float rating;
    private String description;
    private String comment;

    public Book() {}

    public Book(String title, String author, ReadingStatus status, String year, String genre, String language,
                Float rating, String description, String comment) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = status;
        this.genre = genre;
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
