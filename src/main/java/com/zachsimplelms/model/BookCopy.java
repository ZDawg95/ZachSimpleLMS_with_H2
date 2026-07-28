package com.zachsimplelms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    protected BookCopy() {
        // Required by JPA.
    }

    public BookCopy(Book book) {
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }
}
