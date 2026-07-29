package com.zachsimplelms.service;

import com.zachsimplelms.model.BookCopy;

public interface BorrowBookService {

    BookCopy borrowBook(Long bookId, Long borrowerId);

    BookCopy returnBook(Long bookId, Long borrowerId);
}
