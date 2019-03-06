package com.vulpes.velox.exceptions;

public class ForbiddenException extends RuntimeException {

  private String exceptionMessage;

  public ForbiddenException(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public void setExceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }
}
