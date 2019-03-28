package com.vulpes.velox.exceptions.runtimeexceptions;

public class UnauthorizedException extends RuntimeException {

  private String exceptionMessage;

  public UnauthorizedException(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public void setExceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }
}
