package com.matheuscordeiro.libaryapi.api.resource;

import com.matheuscordeiro.libaryapi.api.dto.BookDTO;
import com.matheuscordeiro.libaryapi.model.entity.Book;
import com.matheuscordeiro.libaryapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO dto){
        Book entity = Book.builder()
                        .title(dto.getTitle())
                        .author(dto.getAuthor())
                        .isbn(dto.getIsbn())
                        .build();
        entity = bookService.save(entity);
        return BookDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .isbn(entity.getIsbn())
                .build();
    }
}
