package onlinebookstore;

import java.math.BigDecimal;
import onlinebookstore.entity.Book;
import onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setDescription("testBook");
            book.setPrice(BigDecimal.TEN);
            book.setTitle("Title1");
            book.setAuthor("Author1");
            book.setIsbn("ISBN1");
            book.setCoverImage("CoverImage1");

            bookService.save(book);

            System.out.println(bookService.findAll());
        };
    }
}
