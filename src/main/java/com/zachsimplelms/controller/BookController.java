package com.zachsimplelms.controller;

import com.zachsimplelms.model.Book;
import com.zachsimplelms.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**1. Registers a catalogue book and creates one physical copy. */
    @PutMapping("/registerNewBook")
    @Operation(summary = "Register a new Book to the Library")
    public Book registerBook(@RequestBody Book book) {
        Book registeredBook = bookService.registerBook(book);
        logger.info("Registered book with id {}", registeredBook.getId());
        return registeredBook;
    }

    /**2. Gets a list of all unique books */
    @GetMapping("/getAllBooks")
    @Operation(summary = "Gets a list of all unique books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

}
