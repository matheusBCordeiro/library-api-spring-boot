package com.matheuscordeiro.libaryapi.api.resource;

import com.matheuscordeiro.libaryapi.api.dto.LoanDTO;
import com.matheuscordeiro.libaryapi.api.dto.ReturnedLoanDTO;
import com.matheuscordeiro.libaryapi.model.entity.Book;
import com.matheuscordeiro.libaryapi.model.entity.Loan;
import com.matheuscordeiro.libaryapi.service.BookService;
import com.matheuscordeiro.libaryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO dto) {
        Book book = bookService
                .getBookByIsbn(dto.getIsbn())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found for passed isbn")
                );
        Loan entity = Loan.builder()
                .book(book)
                .customer(dto.getCostumer())
                .loanDate(LocalDate.now())
                .build();
        entity = loanService.save(entity);
        return entity.getId();
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void returnBook(@PathVariable Long id, @RequestBody ReturnedLoanDTO dto) {
        Loan loan = loanService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        loan.setReturned(dto.getReturned());
        loanService.update(loan);
    }
}
