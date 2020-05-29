package com.matheuscordeiro.libaryapi.model.repository;

import com.matheuscordeiro.libaryapi.model.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Must return true when there is a book in the database with informed isbn")
    public void returnTrueWhenIsbnExists() {
        String isbn = "123";
        Book book = createNewBook(isbn);
        entityManager.persist(book);
        boolean exists = repository.existsByIsbn(isbn);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Must return false when there is no book in the database with the informed isbn")
    public void returnFalseWhenIsbnDoesntExist() {
        String isbn = "123";
        boolean exists = repository.existsByIsbn(isbn);
        assertThat(exists).isFalse();
    }


    @Test
    @DisplayName("Must get a book by id")
    public void findById() {
        Book book = createNewBook("123");
        entityManager.persist(book);
        Optional<Book> foundBook = repository.findById(book.getId());
        assertThat(foundBook.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Must save a book")
    public void saveBookTest() {
        Book book = createNewBook("123");
        Book savedBook = repository.save(book);
        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    @DisplayName("Must delete a book")
    public void deleteBookTest() {
        Book book = createNewBook("123");
        entityManager.persist(book);
        Book foundBook = entityManager.find(Book.class, book.getId());
        repository.delete(book);
        Book deletedBook = entityManager.find(Book.class, book.getId());
        assertThat(deletedBook).isNull();
    }

    private Book createNewBook(String isbn) {
        return Book.builder().title("Future").author("Juniot").isbn(isbn).build();
    }

}
