package com.zachsimplelms.service.impl;

import com.zachsimplelms.exception.BadRequestException;
import com.zachsimplelms.exception.ConflictException;
import com.zachsimplelms.model.Borrower;
import com.zachsimplelms.repository.BorrowerRepository;
import com.zachsimplelms.service.BorrowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class BorrowerServiceImpl implements BorrowerService {

    private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final BorrowerRepository borrowerRepository;

    public BorrowerServiceImpl(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    /** Validates and saves a new borrower with its generated database ID. */
    @Override
    public Borrower createBorrower(Borrower borrower) {
        if (borrower == null) {
            throw new BadRequestException("Borrower input cannot be null.");
        }
        if (isBlank(borrower.getName())) {
            throw new BadRequestException("Borrower name cannot be null or blank.");
        }
        if (isBlank(borrower.getEmail())) {
            throw new BadRequestException("Borrower email cannot be null or blank.");
        }

        String email = borrower.getEmail().trim();
        if (borrowerExistsWithEmail(email)) {
            throw new ConflictException("A borrower with this email address already exists.");
        }
        if (!SIMPLE_EMAIL_PATTERN.matcher(email).matches()) {
            throw new BadRequestException("Invalid email format.");
        }

        borrower.setName(borrower.getName().trim());
        borrower.setEmail(email);
        return borrowerRepository.save(borrower);
    }

    @Override
    public boolean borrowerExistsWithEmail(String email) {
        return borrowerRepository.existsByEmailIgnoreCase(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
