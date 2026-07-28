package com.zachsimplelms.service;

import com.zachsimplelms.model.Borrower;
import com.zachsimplelms.repository.BorrowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    /** Saves a new borrower and returns it with its generated database ID. */
    public Borrower createBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public boolean borrowerExistsWithEmail(String email) {
        return borrowerRepository.existsByEmailIgnoreCase(email);
    }

    @Transactional(readOnly = true)
    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }
}
