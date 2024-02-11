package onlinebookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import onlinebookstore.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:database/add-categories.sql",
        "classpath:database/add-books.sql",
    "classpath:database/add-book-category.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/clear-database.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookRepositoryTests {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find book by nonexistent id")
    void findById_NonExistentId_ReturnEmptyOptional() {
        Optional<Book> actual = bookRepository.findById(10L);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    @DisplayName("Find books by nonexistent category id")
    void findAllByCategoriesId_NonExistentCategoryId_ReturnEmptyList() {
        Long categoryId = 3L;
        List<Book> actual = bookRepository.findAllByCategoriesId(categoryId);
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Find books by existent category id")
    void findAllByCategoriesId_ExistentCategory_ReturnNonEmptyList() {
        Long categoryId = 1L;
        List<Book> actual = bookRepository.findAllByCategoriesId(
                categoryId);
        assertEquals(1, actual.size());
        assertEquals(List.of(1L), actual.stream().map(Book::getId).toList());
    }
}
