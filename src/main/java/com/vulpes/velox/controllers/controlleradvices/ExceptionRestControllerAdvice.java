package com.vulpes.velox.controllers.controlleradvices;

import com.vulpes.velox.dtos.errordtos.ErrorResponseDto;
import com.vulpes.velox.dtos.errordtos.ValidationErrorDto;
import com.vulpes.velox.exceptions.runtimeexceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionRestControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorResponseDto notFoundHandler(
      HttpServletRequest page, NotFoundException exception) {
    return getErrorResponseDto(
        exception.getExceptionMessage(), page, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InternalServerErrorException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  ErrorResponseDto internalServerErrorHandler(
      HttpServletRequest page, InternalServerErrorException exception) {
    return getErrorResponseDto(
        exception.getExceptionMessage(), page, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  ErrorResponseDto unauthorizedHandler(
      HttpServletRequest page, UnauthorizedException exception) {
    return getErrorResponseDto(
        exception.getExceptionMessage(), page, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  ErrorResponseDto forbiddenHandler(
      HttpServletRequest page, ForbiddenException exception) {
    return getErrorResponseDto(
        exception.getExceptionMessage(), page, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErrorResponseDto badRequestHandler(
      HttpServletRequest page, BadRequestException exception) {
    return getErrorResponseDto(
        exception.getExceptionMessage(), page, HttpStatus.BAD_REQUEST);
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

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest webRequest) {
    ValidationErrorDto validationErrorDto = getDtoFromExceptionErrors(exception.getBindingResult());

    return super.handleExceptionInternal(
        exception, validationErrorDto, headers, status, webRequest);
  }

  private ValidationErrorDto getDtoFromExceptionErrors(Errors errors) {
    ValidationErrorDto validationErrorDto = new ValidationErrorDto();
    List<String> errorsFound = new ArrayList<>();

    for (ObjectError objectError : errors.getAllErrors()) {
      errorsFound.add(objectError.getDefaultMessage());
    }
    validationErrorDto.message = errorsFound;
    return validationErrorDto;
  }

}
