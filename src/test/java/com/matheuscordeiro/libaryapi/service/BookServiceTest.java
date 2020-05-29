package com.matheuscordeiro.libaryapi.service;

import com.matheuscordeiro.libaryapi.exception.BusinessException;
import com.matheuscordeiro.libaryapi.model.entity.Book;
import com.matheuscordeiro.libaryapi.model.repository.BookRepository;
import com.matheuscordeiro.libaryapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
    BookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Must save a book")
    public void  saveBookTest() {
        Book book = createValidBook();
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(book))
                .thenReturn(
                        Book.builder()
                                .id(1L)
                                .title("Future")
                                .author("Junior")
                                .isbn("001")
                                .build()
                );
        Book savedBook = service.save(book);
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Future");
        assertThat(savedBook.getAuthor()).isEqualTo("Junior");
        assertThat(savedBook.getIsbn()).isEqualTo("001");
    }

    @Test
    @DisplayName("Must should throw a business error when trying to save a book with duplicate isbn")
    public void shouldNotSaveABookWithDuplicatedIsbn() {
        Book book = createValidBook();
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
        Throwable exception = Assertions .catchThrowable(() -> service.save(book));
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn already registered.");
        Mockito.verify(repository, Mockito.never()).save(book);
    }

    @Test
    @DisplayName("Must get a book by id")
    public void getByIdTest() {
        Long id = 1L;
        Book book = createValidBook();
        book.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));
        Optional<Book> foundBook = service.getById(id);
        assertThat(foundBook.isPresent()).isTrue();
        assertThat(foundBook.get().getId()).isEqualTo(id);
        assertThat(foundBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(foundBook.get().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(foundBook.get().getIsbn()).isEqualTo(book.getIsbn());
    }

    @Test
    @DisplayName("Must return empty when obtaining a book with non-existent id in the database")
    public void bookNotFoundByIdTest() {
        Long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Optional<Book> foundBook = service.getById(id);
        assertThat(foundBook.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Must delete a book")
    public void deleteBookTest() {
        Book book = Book.builder().id(1L).build();
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> service.delete(book));
        Mockito.verify(repository, Mockito.times(1)).delete(book);
    }
    @Test
    @DisplayName("Must error should occur when trying to delete a non-existent book")
    public void deleteInvalidBookTest() {
        Book book = new Book();
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class ,() -> service.delete(book));
        Mockito.verify(repository, Mockito.never()).delete(book);
    }

    @Test
    @DisplayName("Must update a book")
    public void updateBookTest() {
        Long id = 1L;
        Book updatingBook = Book.builder().id(1L).build();
        Book updatedBook = createValidBook();
        updatedBook.setId(id);
        Mockito.when(repository.save(updatingBook)).thenReturn(updatedBook);
        Book book = service.update(updatingBook);
        assertThat(book.getId()).isEqualTo(updatedBook.getId());
        assertThat(book.getTitle()).isEqualTo(updatedBook.getTitle());
        assertThat(book.getAuthor()).isEqualTo(updatedBook.getAuthor());
        assertThat(book.getIsbn()).isEqualTo(updatedBook.getIsbn());
    }
    @Test
    @DisplayName("Must error should occur when trying to update a non-existent book")
    public void updateInvalidBookTest() {
        Book book = new Book();
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class ,() -> service.update(book));
        Mockito.verify(repository, Mockito.never()).save(book);
    }

    private Book createValidBook() {
        return Book.builder().author("Junior").title("Futere").isbn("001").build();
    }
}
