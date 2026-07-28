package com.zachsimplelms.service;

import com.zachsimplelms.model.Borrower;

import java.util.List;

public interface BorrowerService {

    Borrower createBorrower(Borrower borrower);

    boolean borrowerExistsWithEmail(String email);

    List<Borrower> getAllBorrowers();
}
