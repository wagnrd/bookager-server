package wagnrd.bookagerserver.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class User {
    private @Id String name;
    private String passwordHash;

    private @OneToMany(targetEntity = Book.class) Set books;

    public User() {}

    public User(@NotNull String name, @NotNull String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }
}
