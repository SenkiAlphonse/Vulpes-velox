package com.vulpes.velox.exceptions.runtimeexceptions;

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
