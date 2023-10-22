package onlinebookstore.repository;

import java.util.List;
import java.util.Optional;
import onlinebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
