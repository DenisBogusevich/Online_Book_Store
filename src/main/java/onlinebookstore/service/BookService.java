package onlinebookstore.service;

import java.util.List;
import onlinebookstore.dto.BookDto;
import onlinebookstore.dto.CreateBookRequestDto;
import onlinebookstore.entity.Book;

public interface BookService {
    Book save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto getBookById(Long id);
}
