package com.matheuscordeiro.libaryapi.service;

import com.matheuscordeiro.libaryapi.model.entity.Book;

import java.util.Optional;

public interface BookService {

    Book save(Book any);

    Optional<Book> getById(Long id);

    void delete(Book book);

    Book update(Book book);
}
