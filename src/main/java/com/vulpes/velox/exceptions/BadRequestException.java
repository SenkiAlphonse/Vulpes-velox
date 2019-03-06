package com.vulpes.velox.exceptions;

public class BadRequestException extends RuntimeException {

  private String exceptionMessage;

  public BadRequestException(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public void setExceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }
}
