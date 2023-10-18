package onlinebookstore.service;

import java.util.List;
import onlinebookstore.entity.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
