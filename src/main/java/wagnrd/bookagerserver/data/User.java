package wagnrd.bookagerserver.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
public class User {
    private @Id @GeneratedValue Long id;
    private String name;
    private String passwordHash;

    private @OneToMany(targetEntity = Book.class) Set books;

    public User() {}

    public User(String name, String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }
}
