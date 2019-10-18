package wagnrd.bookagerserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import wagnrd.bookagerserver.data.User;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        Set<User> users = new HashSet<>();
        users.add(new User("Tabchen", "test123"));

        return args -> {
            for (var user : users) {
                if (!userRepository.exists(Example.of(user)))
                    log.info("Preload " + userRepository.save(user));
            }
        };
    }
}
