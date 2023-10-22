package onlinebookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import onlinebookstore.dto.BookDto;
import onlinebookstore.dto.CreateBookRequestDto;
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
    public BookDto save(CreateBookRequestDto book) {
        return bookMapper.toDto(bookRepository.save((bookMapper.toBook(book))));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
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
}
