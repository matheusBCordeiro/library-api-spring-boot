package com.matheuscordeiro.libaryapi.service.impl;

import com.matheuscordeiro.libaryapi.model.entity.Loan;
import com.matheuscordeiro.libaryapi.model.repository.LoanRepository;
import com.matheuscordeiro.libaryapi.service.LoanService;

public class LoanServiceImpl implements LoanService {
    private LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        return repository.save(loan);
    }
}
