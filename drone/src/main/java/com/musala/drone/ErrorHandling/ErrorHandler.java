package com.musala.drone.ErrorHandling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResponseInfo<String>> emptyResultDataAccessException(
            HttpServletRequest request,
            EmptyResultDataAccessException e) {
        ResponseInfo<String> errorInfo = new ResponseInfo<>(
                HttpStatus.NOT_FOUND.value(),
                "",
                request.getRequestURI(),
                e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseInfo<String>> emptyEntityNotFoundException(
            HttpServletRequest request,
            EntityNotFoundException e) {
        ResponseInfo<String> errorInfo = new ResponseInfo<>(
                HttpStatus.NOT_FOUND.value(),
                "",
                request.getRequestURI(),
                e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseInfo<String>> fileInputLoad(
            HttpServletRequest request,
            IOException e) {
        ResponseInfo<String> errorInfo = new ResponseInfo<>(
                HttpStatus.NOT_FOUND.value(),
                "",
                request.getRequestURI(),
                e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseInfo<String>> dataIntegrityViolationException(
            HttpServletRequest request,
            DataIntegrityViolationException e) {
        ResponseInfo<String> errorInfo = new ResponseInfo<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "",
                request.getRequestURI(),
                "Violation of uniqueness index or primary key.");
        e.printStackTrace();
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ResponseInfo<String>> dataInvalidFormatException(
            HttpServletRequest request,
            InvalidFormatException e) {
        List<String> error = Arrays.asList(e.getMessage().split(";"));
        ResponseInfo<String> errorInfo = new ResponseInfo<>(
                HttpStatus.BAD_REQUEST.value(),
                "",
                request.getRequestURI(),
                error.get(0));
        e.printStackTrace();
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseInfo<String>> illegalArgumentException(
            HttpServletRequest request,
            IllegalArgumentException e) {
        ResponseInfo<String> errorInfo = new ResponseInfo<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "",
                request.getRequestURI(),
                e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseInfo<Map<String, String>>> methodArgumentNotValidException(
            HttpServletRequest request,
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResponseInfo<Map<String, String>> errorInfo = new ResponseInfo<>(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                request.getRequestURI(),
                "Validation conditions for data are not met");
        e.printStackTrace();
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseInfo<String>> methodConstraintViolationException(
            HttpServletRequest request,
            ConstraintViolationException e) {
        ResponseInfo<String> errorInfo = new ResponseInfo<>(
                HttpStatus.BAD_REQUEST.value(),
                "",
                request.getRequestURI(),
                "Weight must be greater than 0");
        e.printStackTrace();
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
