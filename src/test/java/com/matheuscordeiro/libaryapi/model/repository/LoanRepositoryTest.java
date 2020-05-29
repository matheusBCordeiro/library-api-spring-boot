package com.matheuscordeiro.libaryapi.model.repository;

import com.matheuscordeiro.libaryapi.model.entity.Book;
import com.matheuscordeiro.libaryapi.model.entity.Loan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static com.matheuscordeiro.libaryapi.model.repository.BookRepositoryTest.createNewBook;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LoanRepositoryTest {
    @Autowired
    LoanRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Must should check if the borrowed book has been returned")
    public void existsByBookAndNotReturnedTest() {
        Book book = createNewBook("123");
        entityManager.persist(book);
        Loan loan = Loan.builder().book(book).customer("Junior").loanDate(LocalDate.now()).build();
        entityManager.persist(loan);
        boolean exists = repository.existsByBookAndNotReturned(book);
        assertThat(exists).isTrue();
    }
}
