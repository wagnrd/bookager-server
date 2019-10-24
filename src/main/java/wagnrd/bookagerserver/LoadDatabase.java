package wagnrd.bookagerserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import wagnrd.bookagerserver.data.Bookshelf;
import wagnrd.bookagerserver.data.BookshelfRepository;
import wagnrd.bookagerserver.data.User;
import wagnrd.bookagerserver.data.UserRepository;

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

        Set<Bookshelf> bookshelves = new HashSet<>();

        for (int i = 1; i < 2; ++i) {
            var temp = new Bookshelf("TestShelf" + i, tabchen.getName());
            bookshelves.add(temp);
        }

        return args -> {
            for (var user : users) {
                if (!userRepository.exists(Example.of(user)))
                    log.info("Preload " + userRepository.save(user));
            }

            for (var bookShelf : bookshelves) {
                if (!bookShelfRepository.exists(Example.of(bookShelf)))
                    log.info("Preload " + bookShelfRepository.save(bookShelf));
            }
        };
    }
}
