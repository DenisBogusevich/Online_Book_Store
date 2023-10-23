package onlinebookstore.mapper;

import onlinebookstore.dto.BookDto;
import onlinebookstore.dto.CreateBookRequestDto;
import onlinebookstore.entity.Book;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toBook(CreateBookRequestDto bookDto);
}