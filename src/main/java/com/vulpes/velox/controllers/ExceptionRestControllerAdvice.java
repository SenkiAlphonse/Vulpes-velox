package com.vulpes.velox.controllers;

import com.vulpes.velox.dtos.ErrorResponseDto;
import com.vulpes.velox.exceptions.BadEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class ExceptionRestControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadEmailException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErrorResponseDto notFoundHandler(
      HttpServletRequest page, BadEmailException exception) {
    return getErrorResponseDto(
        exception.getExceptionMessage(), page, HttpStatus.NOT_FOUND);
  }

  private ErrorResponseDto getErrorResponseDto(
      String exceptionMessage, HttpServletRequest page, HttpStatus httpStatus) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto();
    errorResponseDto.path = page.getRequestURI();
    errorResponseDto.message = exceptionMessage;
    errorResponseDto.status = httpStatus;
    errorResponseDto.timeStamp = new Date();
    return errorResponseDto;
  }

}
