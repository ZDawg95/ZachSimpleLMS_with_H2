package com.zachsimplelms.service.impl;

import com.zachsimplelms.model.BookCopy;
import com.zachsimplelms.repository.BookCopyRepository;
import com.zachsimplelms.service.BookCopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookCopyServiceImpl.class);

    public BookCopyServiceImpl (BookCopyRepository bookCopyRepository){
        this.bookCopyRepository = bookCopyRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

}
