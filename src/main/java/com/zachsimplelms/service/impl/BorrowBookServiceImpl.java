package com.zachsimplelms.service.impl;

import com.zachsimplelms.model.Book;
import com.zachsimplelms.model.BookCopy;
import com.zachsimplelms.model.Borrower;
import com.zachsimplelms.model.enums.BookStatus;
import com.zachsimplelms.repository.BookCopyRepository;
import com.zachsimplelms.repository.BookRepository;
import com.zachsimplelms.repository.BorrowerRepository;
import com.zachsimplelms.service.BorrowBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowBookServiceImpl implements BorrowBookService {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BorrowerRepository borrowerRepository;

    public BorrowBookServiceImpl(BookRepository bookRepository,
                                 BookCopyRepository bookCopyRepository,
                                 BorrowerRepository borrowerRepository) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    @Transactional
    public BookCopy borrowBook(String isbn, Long borrowerId) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or blank.");
        }
        if (borrowerId == null) {
            throw new IllegalArgumentException("Borrower ID cannot be null.");
        }

        Book book = bookRepository.findByIsbnIgnoreCase(isbn.trim())
                .orElseThrow(() -> new IllegalArgumentException("No book found with this ISBN."));

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new IllegalArgumentException("No borrower found with this ID."));

        BookCopy availableCopy = bookCopyRepository
                .findFirstByBookAndBookStatusOrderByDateAddedAsc(book, BookStatus.AVAILABLE)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No available copies exist for ISBN " + book.getIsbn() + "."));

        availableCopy.setBookStatus(BookStatus.CHECKED_OUT);
        availableCopy.setBorrowedBy(borrower);
        return bookCopyRepository.save(availableCopy);
    }
}
