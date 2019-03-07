package com.vulpes.velox.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDto {

  @NotBlank(message = "Empty parameter: name")
  public String name;
  @NotNull(message = "Missing parameter: quantity")
  public Long quantity;

}
