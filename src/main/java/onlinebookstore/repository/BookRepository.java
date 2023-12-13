package onlinebookstore.repository;

import java.util.List;
import onlinebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByCategoriesId(Long categoryId);
}
