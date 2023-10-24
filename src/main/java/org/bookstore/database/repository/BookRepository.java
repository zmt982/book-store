package org.bookstore.database.repository;

import org.bookstore.database.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<List<Book>> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> save(Book book);

    Optional<Book> updateById(Long id, Book book);

    void deleteById(Long id);
}