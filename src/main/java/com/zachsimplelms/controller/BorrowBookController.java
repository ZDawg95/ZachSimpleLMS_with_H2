package com.zachsimplelms.controller;

import com.zachsimplelms.model.BookCopy;
import com.zachsimplelms.service.BorrowBookService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrowBook")
public class BorrowBookController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowBookController.class);
    private final BorrowBookService borrowBookService;

    public BorrowBookController(BorrowBookService borrowBookService) {
        this.borrowBookService = borrowBookService;
    }

    /** Checks out the next available copy of the requested ISBN. */
    @PutMapping("/borrow")
    @Operation(summary = "Borrow the next available copy of a book")
    public BookCopy borrowBook(@RequestParam String isbn, @RequestParam Long borrowerId) {
        BookCopy checkedOutCopy = borrowBookService.borrowBook(isbn, borrowerId);
        logger.info("Book copy with id {} checked out to borrower id {}", checkedOutCopy.getId(), borrowerId);
        return checkedOutCopy;
    }
}
