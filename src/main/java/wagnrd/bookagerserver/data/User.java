package wagnrd.bookagerserver.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    private @Id String name;
    private String passwordHash;

    //private @OneToMany(mappedBy = "owner") Set<Bookshelf> bookshelves;

    public User() {}

    public User(String name, String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }
}
