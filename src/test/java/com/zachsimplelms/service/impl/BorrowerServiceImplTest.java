package com.zachsimplelms.service.impl;

import com.zachsimplelms.exception.BadRequestException;
import com.zachsimplelms.exception.ConflictException;
import com.zachsimplelms.model.Borrower;
import com.zachsimplelms.repository.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowerServiceImplTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerServiceImpl borrowerService;

    @Test
    void createBorrowerRejectsInvalidEmail() {
        Borrower borrower = new Borrower("Ada Lovelace", "not-an-email");

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> borrowerService.createBorrower(borrower));

        assertEquals("Invalid email format.", exception.getMessage());
        verify(borrowerRepository, never()).save(any());
    }

    @Test
    void createBorrowerRejectsDuplicateEmail() {
        Borrower borrower = new Borrower("Ada Lovelace", "ada@example.com");
        when(borrowerRepository.existsByEmailIgnoreCase("ada@example.com")).thenReturn(true);

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> borrowerService.createBorrower(borrower));

        assertEquals("A borrower with this email address already exists.", exception.getMessage());
        verify(borrowerRepository, never()).save(any());
    }

    @Test
    void createBorrowerTrimsAndSavesValidValues() {
        Borrower borrower = new Borrower("  Ada Lovelace  ", "  ada@example.com  ");
        when(borrowerRepository.existsByEmailIgnoreCase("ada@example.com")).thenReturn(false);
        when(borrowerRepository.save(eq(borrower))).thenReturn(borrower);

        Borrower savedBorrower = borrowerService.createBorrower(borrower);

        assertEquals("Ada Lovelace", savedBorrower.getName());
        assertEquals("ada@example.com", savedBorrower.getEmail());
        verify(borrowerRepository).save(borrower);
    }
}
