package wagnrd.bookagerserver.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
public class BookShelf {
    private @Id @GeneratedValue Long id;
    private String name;

    private String owner;
    private @OneToMany(mappedBy = "bookShelfId") Set<Book> books;

    public BookShelf() {}

    public BookShelf(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }
}
