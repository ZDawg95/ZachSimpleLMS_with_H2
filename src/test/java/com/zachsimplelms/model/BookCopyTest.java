package com.zachsimplelms.model;

import com.zachsimplelms.model.enums.BookStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookCopyTest {

    @Test
    void newBookCopyDefaultsToAvailable() {
        BookCopy copy = new BookCopy(new Book("9780132350884", "Clean Code", "Robert C. Martin"));

        assertEquals(BookStatus.AVAILABLE, copy.getBookStatus());
    }

    @Test
    void prePersistSetsDateAddedWithoutFractionalSeconds() {
        BookCopy copy = new BookCopy(new Book("9780132350884", "Clean Code", "Robert C. Martin"));

        copy.setDateAdded();

        assertNotNull(copy.getDateAdded());
        assertEquals(0, copy.getDateAdded().getNano());
    }
}
