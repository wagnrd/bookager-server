package wagnrd.bookagerserver;

import org.springframework.data.jpa.repository.JpaRepository;
import wagnrd.bookagerserver.data.User;

public interface UserRepository extends JpaRepository<User, String> {}
