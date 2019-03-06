package com.vulpes.velox.controllers;

import com.vulpes.velox.exceptions.BadEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionRestControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadEmailException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorResponseDto notFoundHandler(
      HttpServletRequest page, BadEmailException exception) {
    return getErrorResponseDto(
        exception.getExceptionMessage(), page, HttpStatus.NOT_FOUND);
  }

}
