package wagnrd.bookagerserver.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Book {
    private @Id @GeneratedValue Long id;
    private Long bookShelfId;

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
}
