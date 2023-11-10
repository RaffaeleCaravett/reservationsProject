package com.example.reservationsProject.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;
@Getter
public class BadRequestExceptions extends RuntimeException {
    private List<ObjectError> errorsList;
    public BadRequestExceptions(String message){
        super(message);
    }

    public BadRequestExceptions(List<ObjectError> errors){
        this.errorsList = errors;
    }
}