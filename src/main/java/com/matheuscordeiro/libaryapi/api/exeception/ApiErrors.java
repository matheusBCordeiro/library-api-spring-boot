package com.matheuscordeiro.libaryapi.api.exeception;

import com.matheuscordeiro.libaryapi.exception.BusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    private List<String> errors;

    public ApiErrors(BusinessException e) {
        this.errors= Arrays.asList(e.getMessage());
    }

    public ApiErrors(ResponseStatusException e) {
        this.errors= Arrays.asList(e.getReason());
    }

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));
    }
}
