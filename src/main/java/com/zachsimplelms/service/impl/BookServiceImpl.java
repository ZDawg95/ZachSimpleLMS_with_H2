package com.zachsimplelms.service.impl;

import com.zachsimplelms.model.Book;
import com.zachsimplelms.model.BookCopy;
import com.zachsimplelms.repository.BookCopyRepository;
import com.zachsimplelms.repository.BookRepository;
import com.zachsimplelms.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    public BookServiceImpl(BookRepository bookRepository, BookCopyRepository bookCopyRepository) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    @Transactional
    public Book registerBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        if (isBlank(book.getIsbn()) || isBlank(book.getTitle()) || isBlank(book.getAuthor())) {
            throw new IllegalArgumentException("ISBN, title, and author are required.");
        }

        String isbn = book.getIsbn().trim();
        Book catalogueBook = bookRepository.findByIsbnIgnoreCase(isbn).orElse(null);

        if (catalogueBook == null) {
            catalogueBook = bookRepository.save(book);
            logger.info("Added new book with ISBN {}", isbn);
        } else { //one isbn can only be tied to a specific author-title combination
            if (!catalogueBook.getTitle().equals(book.getTitle().trim())
                    || !catalogueBook.getAuthor().equals(book.getAuthor().trim())) {
                throw new IllegalArgumentException(
                        "ISBN already exists with a different title or author.");
            }
            logger.info("ISBN {} already exists; adding another physical copy", isbn);
        }

        bookCopyRepository.save(new BookCopy(catalogueBook));
        return catalogueBook;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
