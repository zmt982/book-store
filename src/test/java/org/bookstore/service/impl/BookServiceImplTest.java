package org.bookstore.service.impl;

import org.bookstore.database.entity.Book;
import org.bookstore.database.repository.BookRepository;
import org.bookstore.service.mapper.BookMapper;
import org.bookstore.service.model.BookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;
    private BookDto bookDto;
    private Book book;

    @BeforeEach
    void init() {
        bookDto = new BookDto();
        bookDto.setAuthor("Dostoevskiy");
        bookDto.setName("Prestuplenie i nakazanie");

        book = new Book();
        book.setId(1L);
        book.setAuthor("Dostoevskiy");
        book.setName("Prestuplenie i nakazanie");
    }

    @Test
    void getAll() {
        List<Book> books = Collections.singletonList(book);
        List<BookDto> expected = Collections.singletonList(bookDto);

        when(bookRepository.findAll()).thenReturn(Optional.ofNullable(books));
        when(bookMapper.toDto(books)).thenReturn(expected);

        List<BookDto> result = bookService.getAll();

        assertEquals(expected, result);

        verify(bookRepository).findAll();
        verify(bookMapper).toDto(books);
    }

    @Test
    void getById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.getById(1L);

        assertEquals(bookDto, result);

        verify(bookRepository).findById(1L);
        verify(bookMapper).toDto(book);
    }

    @Test
    void add() {
        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(Optional.ofNullable(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.add(bookDto);

        assertEquals(bookDto, result);

        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void updateById() {
        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.updateById(1L, book)).thenReturn(Optional.ofNullable(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.updateById(1L, bookDto);

        assertEquals(bookDto, result);

        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).updateById(1l, book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void deleteById() {
        bookService.deleteById(1L);

        verify(bookRepository).deleteById(1L);
    }
}