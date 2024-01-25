package onlinebookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import onlinebookstore.dto.book.BookDto;
import onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import onlinebookstore.dto.book.CreateBookRequestDto;
import onlinebookstore.entity.Book;
import onlinebookstore.entity.Category;
import onlinebookstore.mapper.BookMapper;
import onlinebookstore.repository.BookRepository;
import onlinebookstore.repository.CategoryRepository;
import onlinebookstore.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookDto) {
        Set<Category> categories = getCategories(bookDto);
        Book book = bookMapper.toBook(bookDto);
        book.setCategories(categories);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                        .map(bookMapper::toDto)
                        .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookRepository.findById(id)
                            .map(bookMapper::toDto)
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "can't find book with such id: %d".formatted(id)));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookDto update(Long id, CreateBookRequestDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id: " + id));
        bookMapper.updateBook(id,bookDto, book);
        addBookCategories(bookDto.categoryIds(), book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id) {
        return bookRepository.findAllByCategoriesId(id).stream()
                        .map(bookMapper::toDtoWithoutCategories)
                        .toList();
    }

    private Set<Category> getCategories(CreateBookRequestDto bookDto) {
        return bookDto.categoryIds().stream()
                .map(categoryRepository::getReferenceById)
                .collect(Collectors.toSet());
    }
    private void addBookCategories(List<Long> categoryIds, Book book) {
        book.setCategories(categoryIds.stream()
                .map(categoryRepository::getReferenceById)
                .collect(Collectors.toSet())
        );
    }
}
