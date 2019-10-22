package wagnrd.bookagerserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import wagnrd.bookagerserver.data.BookShelf;
import wagnrd.bookagerserver.data.User;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, BookShelfRepository bookShelfRepository) {
        Set<User> users = new HashSet<>();
        User tabchen = new User("Tabchen", "test123");
        users.add(tabchen);
        User denni = new User("Denni", "test123");
        users.add(denni);

        Set<BookShelf> bookShelves = new HashSet<>();
        BookShelf testShelf = new BookShelf("TestShelf", tabchen.getName());
        bookShelves.add(testShelf);
        BookShelf testShelf2 = new BookShelf("TestShelf2", denni.getName());
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
