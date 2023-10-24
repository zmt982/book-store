package org.bookstore.controller;

import org.bookstore.service.BookService;
import org.bookstore.service.model.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-store")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/bookById/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping("/addBook")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto addDto) {
        return ResponseEntity.ok(bookService.add(addDto));
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<BookDto> updateBookById(@PathVariable Long id, @RequestBody BookDto updateDto) {
        return ResponseEntity.ok(bookService.updateById(id, updateDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(id);
    }

}