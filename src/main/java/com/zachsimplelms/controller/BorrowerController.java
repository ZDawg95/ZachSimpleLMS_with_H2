package com.zachsimplelms.controller;

import com.zachsimplelms.model.Borrower;
import com.zachsimplelms.service.BorrowerService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    /** 1. PUT Endpoint: Create a new borrower. */
    @PutMapping("/register")
    @Operation(summary = "Register a new borrower to the Library")
    public String registerBorrower(@RequestBody Borrower borrower) {
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

}
