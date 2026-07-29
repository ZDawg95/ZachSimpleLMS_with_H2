package com.zachsimplelms.service;

import com.zachsimplelms.model.BookCopy;

public interface BorrowBookService {

    BookCopy borrowBook(String isbn, Long borrowerId);

    BookCopy returnBook(String isbn, Long borrowerId);
}
