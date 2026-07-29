package com.zachsimplelms.service.impl;

import com.zachsimplelms.exception.ConflictException;
import com.zachsimplelms.model.Book;
import com.zachsimplelms.model.BookCopy;
import com.zachsimplelms.repository.BookCopyRepository;
import com.zachsimplelms.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookCopyRepository bookCopyRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void registerBookCreatesACatalogueBookAndPhysicalCopyForNewIsbn() {
        Book book = new Book("9780132350884", "Clean Code", "Robert C. Martin");
        when(bookRepository.findByIsbnIgnoreCase(book.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);
        when(bookCopyRepository.save(any(BookCopy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book savedBook = bookService.registerBook(book);

        ArgumentCaptor<BookCopy> copyCaptor = ArgumentCaptor.forClass(BookCopy.class);
        assertEquals(book, savedBook);
        verify(bookRepository).save(book);
        verify(bookCopyRepository).save(copyCaptor.capture());
        assertEquals(book, copyCaptor.getValue().getBook());
    }

    @Test
    void registerBookRejectsConflictingDetailsForExistingIsbn() {
        Book existingBook = new Book("9780132350884", "Clean Code", "Robert C. Martin");
        Book incomingBook = new Book("9780132350884", "Other Title", "Another Author");
        when(bookRepository.findByIsbnIgnoreCase(incomingBook.getIsbn()))
                .thenReturn(Optional.of(existingBook));

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> bookService.registerBook(incomingBook));

        assertEquals("ISBN already exists with a different title or author.", exception.getMessage());
        verify(bookRepository, never()).save(any());
        verify(bookCopyRepository, never()).save(any());
    }
}
