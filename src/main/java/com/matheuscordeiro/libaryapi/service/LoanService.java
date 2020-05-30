package com.matheuscordeiro.libaryapi.service;

import com.matheuscordeiro.libaryapi.api.dto.LoanFilterDTO;
import com.matheuscordeiro.libaryapi.model.entity.Book;
import com.matheuscordeiro.libaryapi.model.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Loan loan);

    Page<Loan> find(LoanFilterDTO filterDTO, Pageable page);

    Page<Loan> getLoansByBook(Book book, Pageable pageable);
}
