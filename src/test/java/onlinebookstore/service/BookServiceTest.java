package onlinebookstore.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import onlinebookstore.dto.book.BookDto;
import onlinebookstore.dto.book.CreateBookRequestDto;
import onlinebookstore.entity.Book;
import onlinebookstore.entity.Category;
import onlinebookstore.mapper.BookMapper;
import onlinebookstore.mapper.impl.BookMapperImpl;
import onlinebookstore.repository.BookRepository;
import onlinebookstore.repository.CategoryRepository;
import onlinebookstore.service.impl.BookServiceImpl;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookRepository bookRepository;

    @Spy
    private BookMapper bookMapper = new BookMapperImpl();
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Find book by existent id")
    void getById_WithValidId_ShouldReturnValidBookDto() {
        Long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setPrice(BigDecimal.valueOf(10));
        book.setIsbn("1234567890");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.findById(id);
        assertThat(bookDto)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("title", "Clean Code")
                .hasFieldOrPropertyWithValue("author", "Robert Martin")
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(10));
    }

    @Test
    @DisplayName("Create new valid book")
    void save_ValidCreateBookRequestDto_ReturnBookDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "Clean Code",
                "Robert Martin",
                "1234567890",
                BigDecimal.valueOf(10),
                null,
                null,
                List.of(1L, 2L)
        );

        Category category1 = new Category();
        category1.setId(1L);
        Category category2 = new Category();
        category2.setId(2L);

        Book book = new Book();
        book.setTitle(requestDto.title());
        book.setAuthor(requestDto.author());
        book.setIsbn(requestDto.isbn());
        book.setPrice(requestDto.price());
        book.setCategories(Set.of(category1, category2));

        when(categoryRepository.getReferenceById(anyLong())).thenReturn(category1, category2);
        when(bookRepository.save(book)).thenReturn(book);

        BookDto bookDto = bookService.save(requestDto);

        assertThat(bookDto)
                .hasFieldOrPropertyWithValue("title", "Clean Code")
                .hasFieldOrPropertyWithValue("author", "Robert Martin")
                .hasFieldOrPropertyWithValue("isbn", "1234567890")
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(10))
                .extracting("categoryIds")
                .asInstanceOf(InstanceOfAssertFactories.list(Long.class))
                .containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    @DisplayName("find nonexistent book by id")
    void getById_WithNonexistentId_ShouldReturnNull() {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("can't find book with such id: %d".formatted(id));
    }

    @Test
    @DisplayName("Delete book by existent id")
    void deleteById_WithExistentId_ShouldDeleteBook() {
        Long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setPrice(BigDecimal.valueOf(10));
        book.setIsbn("1234567890");

        bookService.deleteById(id);

        assertThatThrownBy(() -> bookService.findById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("can't find book with such id: %d".formatted(id));
    }

    @Test
    @DisplayName("Delete book by nonexistent id")
    void deleteById_WithNonexistentId_ShouldThrowException() {
        Long id = -1L;

        bookService.deleteById(id);

        assertThatThrownBy(() -> bookService.findById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("can't find book with such id: %d".formatted(id));
    }

    @Test
    @DisplayName("update")
        void update_ValidCase_ShouldReturnBookDto() {
        final Long id = 1L;
        final CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "Effective Java",
                "Joshua Bloch",
                "1234567890",
                BigDecimal.valueOf(30),
                null,
                null,
                Collections.emptyList()
        );

        Category category = new Category();
        category.setId(1L);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setIsbn("123456789");
        book.setPrice(BigDecimal.valueOf(30));
        book.setCategories(Set.of(category));

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        BookDto bookDto = bookService.update(id, requestDto);

        assertThat(bookDto)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("title", "Effective Java")
                .hasFieldOrPropertyWithValue("author", "Joshua Bloch")
                .hasFieldOrPropertyWithValue("isbn", "1234567890")
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(30))
                .hasFieldOrPropertyWithValue("categoryIds", Collections.emptyList());

    }
}
