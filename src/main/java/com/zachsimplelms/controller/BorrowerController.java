package com.zachsimplelms.controller;

import com.zachsimplelms.model.Borrower;
import com.zachsimplelms.service.BorrowerService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);
    private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    /** 1. PUT Endpoint: Create a new borrower. */
    @PutMapping("/register")
    @Operation(summary = "Register a new borrower to the Library")
    public String registerBorrower(@RequestBody Borrower borrower) {
        validateBorrower(borrower);
        Borrower savedBorrower = borrowerService.createBorrower(borrower);
        logger.info("Created borrower with id {}", savedBorrower.getId());
        return "Borrower registered successfully with id " + savedBorrower.getId();
    }

    /** 2. GET Endpoint: Get ALL Borrowers in the repository */
    @GetMapping("/getAllBorrowers")
    @Operation(summary = "Get all registered borrowers in the library")
    public List<Borrower> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    private void validateBorrower(Borrower borrower) {
        if (borrower == null) {
            throw new IllegalArgumentException("Borrower input cannot be null.");
        }
        if (isBlank(borrower.getName())) {
            throw new IllegalArgumentException("Borrower name cannot be null or blank.");
        }
        if (isBlank(borrower.getEmail())) {
            throw new IllegalArgumentException("Borrower email cannot be null or blank.");
        }

        String email = borrower.getEmail().trim();
        if (borrowerService.borrowerExistsWithEmail(email)) {
            throw new IllegalArgumentException("A borrower with this email address already exists.");
        }
        if (!SIMPLE_EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
