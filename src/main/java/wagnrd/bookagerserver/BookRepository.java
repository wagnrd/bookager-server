package wagnrd.bookagerserver;

import org.springframework.data.jpa.repository.JpaRepository;
import wagnrd.bookagerserver.data.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}
