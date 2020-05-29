package com.matheuscordeiro.libaryapi.service;

import com.matheuscordeiro.libaryapi.model.entity.Loan;

import java.util.Optional;

public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Loan loan);
}
