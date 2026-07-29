package com.zachsimplelms.service.impl;

import com.zachsimplelms.exception.BadRequestException;
import com.zachsimplelms.exception.ConflictException;
import com.zachsimplelms.exception.ResourceNotFoundException;
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
    public BookCopy borrowBook(Long bookId, Long borrowerId) {
        if (bookId == null) {
            throw new BadRequestException("Book ID cannot be null.");
        }
        if (borrowerId == null) {
            throw new BadRequestException("Borrower ID cannot be null.");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("No book found with this ID."));

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("No borrower found with this ID."));

        if (bookCopyRepository.existsByBookAndBorrowedByAndBookStatus(
                book, borrower, BookStatus.CHECKED_OUT)) {
            throw new ConflictException(
                    "A user is not allowed to borrow more than 1 copy of the same book.");
        }

        BookCopy availableCopy = bookCopyRepository
                .findFirstByBookAndBookStatusOrderByDateAddedAsc(book, BookStatus.AVAILABLE)
                .orElseThrow(() -> new ConflictException(
                        "No available copies exist for book ID " + book.getId() + "."));

        availableCopy.setBookStatus(BookStatus.CHECKED_OUT);
        availableCopy.setBorrowedBy(borrower);
        return bookCopyRepository.save(availableCopy);
    }

    @Override
    @Transactional
    public BookCopy returnBook(Long bookId, Long borrowerId) {
        if (bookId == null) {
            throw new BadRequestException("Book ID cannot be null.");
        }
        if (borrowerId == null) {
            throw new BadRequestException("Borrower ID cannot be null.");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("No book found with this ID."));

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new ResourceNotFoundException("No borrower found with this ID."));

        BookCopy checkedOutCopy = bookCopyRepository
                .findFirstByBookAndBorrowedByAndBookStatus(
                        book, borrower, BookStatus.CHECKED_OUT)
                .orElseThrow(() -> new ConflictException(
                        "This borrower has not checked out this book."));

        checkedOutCopy.setBorrowedBy(null);
        checkedOutCopy.setBookStatus(BookStatus.AVAILABLE);
        return bookCopyRepository.save(checkedOutCopy);
    }
}
