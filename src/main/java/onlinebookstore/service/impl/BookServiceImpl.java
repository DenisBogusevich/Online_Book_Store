package onlinebookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import onlinebookstore.dto.BookDto;
import onlinebookstore.dto.CreateBookRequestDto;
import onlinebookstore.entity.Book;
import onlinebookstore.mapper.BookMapper;
import onlinebookstore.repository.BookRepository;
import onlinebookstore.service.BookService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Book save(CreateBookRequestDto book) {
        return bookRepository.save(bookMapper.toBook(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                        .map(bookMapper::toDto)
                        .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                            .map(bookMapper::toDto)
                            .orElseThrow(() -> new EntityNotFoundException("Can`t find"
                                    + "book with id:" + id));
    }
}
