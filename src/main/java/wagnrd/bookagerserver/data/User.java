package wagnrd.bookagerserver.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@Entity
public class User {
    private @Id @GeneratedValue Long id;
    private String name;
    private String hash;

    private @ManyToMany(targetEntity = Book.class) Set books;

    public User() {}

    public User(String name, String hash) {
        this.name = name;
        this.hash = hash;
    }
}
