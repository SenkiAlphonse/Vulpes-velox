package com.vulpes.velox.services;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.IdentifiedProduct;

import java.util.List;

public interface IdentifiedProductService {

  List<IdentifiedProduct> getAll();

  IdentifiedProduct getEntityFromDto(ProductDto productDto);

}
