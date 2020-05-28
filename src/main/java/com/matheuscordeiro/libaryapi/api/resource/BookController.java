package com.matheuscordeiro.libaryapi.api.resource;

import com.matheuscordeiro.libaryapi.api.dto.BookDTO;
import com.matheuscordeiro.libaryapi.api.exeception.ApiErrors;
import com.matheuscordeiro.libaryapi.exception.BusinessException;
import com.matheuscordeiro.libaryapi.model.entity.Book;
import com.matheuscordeiro.libaryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;
    private ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto){
        Book entity = modelMapper.map(dto, Book.class);
        entity = bookService.save(entity);
        return modelMapper.map(entity, BookDTO.class);
    }

    @GetMapping("{id}")
    public BookDTO getById(@PathVariable Long id) {
        return bookService
                .getById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Book book = bookService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bookService.delete(book);
    }

    @PutMapping({"id"})
    public BookDTO update(@PathVariable Long id, BookDTO dto) {
        return bookService.getById(id).map(book -> {
            book.setTitle(dto.getTitle());
            book.setAuthor(dto.getAuthor());
            book = bookService.update(book);
            return modelMapper.map(book, BookDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException e) {
        return new ApiErrors(e);
    }
}
