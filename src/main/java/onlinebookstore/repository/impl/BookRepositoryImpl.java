package onlinebookstore.repository.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import onlinebookstore.entity.Book;
import onlinebookstore.exception.DataProcessingException;
import onlinebookstore.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        try {
            sessionFactory.inTransaction(sessionFactory -> sessionFactory.persist(book));
            return book;
        } catch (Exception e) {
            throw new DataProcessingException("Can't save book to the DB, book: " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book", Book.class).getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Can't get all books from DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try {
            return Optional.ofNullable(sessionFactory
                    .fromSession(s -> s.find(Book.class, id)));
        } catch (Exception e) {
            throw new EntityNotFoundException("Can`t get Book from DB with id: "
                    + id, e);
        }
    }
}