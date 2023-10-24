package org.bookstore.service.mapper.impl;

import org.bookstore.database.entity.Book;
import org.bookstore.service.mapper.BookMapper;
import org.bookstore.service.model.BookDto;
import org.bookstore.service.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book entity) {
        if (entity == null) {
            return null;
        }
        final BookDto bookDto = new BookDto();
        bookDto.setAuthor(entity.getAuthor());
        bookDto.setName(entity.getName());
        if (entity.getUser() != null) {
            final UserDto userDto = new UserDto();
            userDto.setUsername(entity.getUser().getUsername());
            bookDto.setUserDto(userDto);
        }
        return bookDto;
    }

    @Override
    public Book toEntity(BookDto dto) {
        if (dto == null) {
            return null;
        }
        final Book book = new Book();
        book.setAuthor(dto.getAuthor());
        book.setName(dto.getName());
        return book;
    }

    @Override
    public List<BookDto> toDto(List<Book> entities) {
        return entities.stream().map(this::toDto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<Book> toEntity(List<BookDto> dtoList) {
        return dtoList.stream().map(this::toEntity).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
