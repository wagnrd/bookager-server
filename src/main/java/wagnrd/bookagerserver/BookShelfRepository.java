package wagnrd.bookagerserver;

import org.springframework.data.jpa.repository.JpaRepository;
import wagnrd.bookagerserver.data.BookShelf;

public interface BookShelfRepository extends JpaRepository<BookShelf, Long> {}
