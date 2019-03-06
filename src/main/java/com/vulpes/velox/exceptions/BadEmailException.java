package com.vulpes.velox.exceptions;

public class BadEmailException extends RuntimeException {
  private String exceptionMessage;

  public BadEmailException(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public void setExceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }
}
