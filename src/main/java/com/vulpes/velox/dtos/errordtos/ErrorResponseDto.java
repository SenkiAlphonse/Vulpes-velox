package com.vulpes.velox.dtos.errordtos;

import org.springframework.http.HttpStatus;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class ErrorResponseDto {

  public HttpStatus status;
  public String message;
  public String path;

  @Temporal(TemporalType.TIMESTAMP)
  public Date timeStamp;
}
