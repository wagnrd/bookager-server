package wagnrd.bookagerserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import wagnrd.bookagerserver.data.Bookshelf;
import wagnrd.bookagerserver.data.User;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, BookshelfRepository bookShelfRepository) {
        Set<User> users = new HashSet<>();
        User tabchen = new User("Tabchen", "test123");
        users.add(tabchen);
        User denni = new User("Denni", "test123");
        users.add(denni);

        Set<Bookshelf> bookShelves = new HashSet<>();
        Bookshelf testShelf = new Bookshelf("TestShelf", tabchen.getName());
        bookShelves.add(testShelf);
        Bookshelf testShelf2 = new Bookshelf("TestShelf2", tabchen.getName());
        bookShelves.add(testShelf2);

        return args -> {
            for (var user : users) {
                if (!userRepository.exists(Example.of(user)))
                    log.info("Preload " + userRepository.save(user));
            }

            for (var bookShelf : bookShelves) {
                if (!bookShelfRepository.exists(Example.of(bookShelf)))
                    log.info("Preload " + bookShelfRepository.save(bookShelf));
            }
        };
    }
}
