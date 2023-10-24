package org.bookstore.service;

import org.bookstore.service.model.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto getById(Long id);

    BookDto add(BookDto addDto);

    BookDto updateById(Long id, BookDto updateDto);

    void deleteById(Long id);
}