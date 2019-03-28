package com.vulpes.velox.exceptions.runtimeexceptions;

public class InternalServerErrorException extends RuntimeException {

  private String exceptionMessage;

  public InternalServerErrorException(String messageErrorResponse) {
    this.exceptionMessage = messageErrorResponse;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public void setExceptionMessage(String messageErrorResponse) {
    this.exceptionMessage = messageErrorResponse;
  }
}
