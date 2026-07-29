package com.zachsimplelms.service;

import com.zachsimplelms.model.Book;

import java.util.List;

public interface BookService {
    Book registerBook (Book book);
    List<Book> getAllBooks();
}
