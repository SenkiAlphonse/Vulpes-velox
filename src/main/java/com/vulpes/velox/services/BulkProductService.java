package com.vulpes.velox.services;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.BulkProduct;

import java.util.List;

public interface BulkProductService {

  List<BulkProduct> getAll();

  BulkProduct getEntityFromDto(ProductDto productDto);

}
