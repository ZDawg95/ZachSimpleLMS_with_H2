package com.zachsimplelms.repository;

import com.zachsimplelms.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

    boolean existsByEmailIgnoreCase(String email);
}
