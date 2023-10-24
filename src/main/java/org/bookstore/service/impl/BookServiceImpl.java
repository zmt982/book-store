package org.bookstore.service.impl;

import org.bookstore.database.entity.Book;
import org.bookstore.database.repository.BookRepository;
import org.bookstore.service.BookService;
import org.bookstore.service.mapper.BookMapper;
import org.bookstore.service.model.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDto> getAll() {
        Optional<List<Book>> optionalBooks = bookRepository.findAll();
        List<Book> books = optionalBooks.orElseThrow(() -> new RuntimeException("No books found"));
        return bookMapper.toDto(books);
    }

    @Override
    public BookDto getById(Long id) {
        Optional<Book> optionalBookToFind = bookRepository.findById(id);
        Book bookToFind = optionalBookToFind.orElseThrow(() -> new RuntimeException("No book found"));
        return bookMapper.toDto(bookToFind);
    }

    @Override
    public BookDto add(BookDto addDto) {
        Book bookToAdd = bookMapper.toEntity(addDto);
        Optional<Book> optionalBookToAdd = bookRepository.save(bookToAdd);
        bookToAdd = optionalBookToAdd.orElseThrow(() -> new RuntimeException("No book added"));
        return bookMapper.toDto(bookToAdd);
    }

    @Override
    public BookDto updateById(Long id, BookDto updateDto) {
        Book bookToUpdate = bookMapper.toEntity(updateDto);
        Optional<Book> optionalBookToUpdate = bookRepository.updateById(id, bookToUpdate);
        bookToUpdate = optionalBookToUpdate.orElseThrow(() -> new RuntimeException("No book updated"));
        return bookMapper.toDto(bookToUpdate);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}