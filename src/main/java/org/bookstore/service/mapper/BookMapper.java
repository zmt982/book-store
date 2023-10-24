package org.bookstore.service.mapper;

import org.bookstore.database.entity.Book;
import org.bookstore.service.model.BookDto;

import java.util.List;

public interface BookMapper {
    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);

    List<BookDto> toDto(List<Book> entities);

    List<Book> toEntity(List<BookDto> dtoList);
}
