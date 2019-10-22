package wagnrd.bookagerserver.data;

import lombok.Data;
import org.springframework.data.domain.Example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Bookshelf {
    private @Id @GeneratedValue Long id;
    private String name;

    private String owner;
    //private @OneToMany(mappedBy = "bookshelfId") Set<Book> books;

    public Bookshelf() {}

    public Bookshelf(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    // Owner query
    public static Example<Bookshelf> ownerQuery(String owner) {
        var bookshelf = new Bookshelf();
        bookshelf.owner = owner;

        return Example.of(bookshelf);
    }
}
