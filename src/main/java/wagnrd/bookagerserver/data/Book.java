package wagnrd.bookagerserver.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Book {
    private @Id @GeneratedValue Long id;
    private String title;
    private String author;
    private Short year;
    private ReadingStatus status;
    private Byte rating;
    private String description;
    private String comments;

    public Book() {}

    public Book(@NotNull String title, @NotNull String author, Short year, @NotNull ReadingStatus status, Byte rating,
                String description, String comments) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = status;
        this.rating = rating;
        this.description = description;
        this.comments = comments;
    }
}
