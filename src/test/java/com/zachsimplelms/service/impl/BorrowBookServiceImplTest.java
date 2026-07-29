package com.zachsimplelms.service.impl;

import com.zachsimplelms.exception.BadRequestException;
import com.zachsimplelms.model.Book;
import com.zachsimplelms.model.BookCopy;
import com.zachsimplelms.model.Borrower;
import com.zachsimplelms.model.enums.BookStatus;
import com.zachsimplelms.repository.BookCopyRepository;
import com.zachsimplelms.repository.BookRepository;
import com.zachsimplelms.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowBookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookCopyRepository bookCopyRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowBookServiceImpl borrowBookService;

    @Test
    void borrowBookRejectsNullBookId() {
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> borrowBookService.borrowBook(null, 10000L));

        assertEquals("Book ID cannot be null.", exception.getMessage());
    }

    @Test
    void borrowBookChecksOutAvailableCopy() {
        Long bookId = 100L;
        Long borrowerId = 10000L;
        Book book = new Book("9780132350884", "Clean Code", "Robert C. Martin");
        Borrower borrower = new Borrower("Ada Lovelace", "ada@example.com");
        BookCopy copy = new BookCopy(book);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookCopyRepository.existsByBookAndBorrowedByAndBookStatus(
                book, borrower, BookStatus.CHECKED_OUT)).thenReturn(false);
        when(bookCopyRepository.findFirstByBookAndBookStatusOrderByDateAddedAsc(
                book, BookStatus.AVAILABLE)).thenReturn(Optional.of(copy));
        when(bookCopyRepository.save(copy)).thenReturn(copy);

        BookCopy checkedOutCopy = borrowBookService.borrowBook(bookId, borrowerId);

        assertEquals(BookStatus.CHECKED_OUT, checkedOutCopy.getBookStatus());
        assertEquals(borrower, checkedOutCopy.getBorrowedBy());
        verify(bookCopyRepository).save(copy);
    }

    @Test
    void returnBookMarksBorrowedCopyAvailable() {
        Long bookId = 100L;
        Long borrowerId = 10000L;
        Book book = new Book("9780132350884", "Clean Code", "Robert C. Martin");
        Borrower borrower = new Borrower("Ada Lovelace", "ada@example.com");
        BookCopy copy = new BookCopy(book);
        copy.setBookStatus(BookStatus.CHECKED_OUT);
        copy.setBorrowedBy(borrower);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookCopyRepository.findFirstByBookAndBorrowedByAndBookStatus(
                book, borrower, BookStatus.CHECKED_OUT)).thenReturn(Optional.of(copy));
        when(bookCopyRepository.save(copy)).thenReturn(copy);

        BookCopy returnedCopy = borrowBookService.returnBook(bookId, borrowerId);

        assertEquals(BookStatus.AVAILABLE, returnedCopy.getBookStatus());
        assertNull(returnedCopy.getBorrowedBy());
        verify(bookCopyRepository).save(copy);
    }
}
