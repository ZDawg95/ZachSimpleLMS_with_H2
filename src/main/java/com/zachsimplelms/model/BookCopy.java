package com.zachsimplelms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zachsimplelms.model.enums.BookStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * A physical copy of a catalogue book. Several copies may point to one Book.
 */
@Entity
@Table(name = "book_copies")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus bookStatus = BookStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrowedBy;

    @Column(name = "date_added", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateAdded;

    protected BookCopy() {
        // Required by JPA.
    }

    public BookCopy(Book book) {
        this.book = book;
    }

    @PrePersist
    protected void setDateAdded() {
        if (dateAdded == null) {
            dateAdded = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        }
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Borrower getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(Borrower borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }
}
