package com.zachsimplelms.repository;

import com.zachsimplelms.model.BookCopy;
import com.zachsimplelms.model.Book;
import com.zachsimplelms.model.Borrower;
import com.zachsimplelms.model.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    boolean existsByBookAndBorrowedByAndBookStatus(
            Book book, Borrower borrowedBy, BookStatus bookStatus);

    Optional<BookCopy> findFirstByBookAndBorrowedByAndBookStatus(
            Book book, Borrower borrowedBy, BookStatus bookStatus);

    Optional<BookCopy> findFirstByBookAndBookStatusOrderByDateAddedAsc(
            Book book, BookStatus bookStatus);
}
