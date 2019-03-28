package com.vulpes.velox.exceptions.runtimeexceptions;

public class NotFoundException extends RuntimeException {

  private String exceptionMessage;

  public NotFoundException(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public void setExceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }
}
