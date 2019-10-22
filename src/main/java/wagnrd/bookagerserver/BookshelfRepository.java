package wagnrd.bookagerserver;

import org.springframework.data.jpa.repository.JpaRepository;
import wagnrd.bookagerserver.data.Bookshelf;

public interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {}
