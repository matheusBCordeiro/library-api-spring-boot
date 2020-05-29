package com.matheuscordeiro.libaryapi.model.repository;

import com.matheuscordeiro.libaryapi.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
