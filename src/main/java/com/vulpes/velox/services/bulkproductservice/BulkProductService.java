package com.vulpes.velox.services.bulkproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.BulkProduct;

import java.util.List;

public interface BulkProductService {

  List<BulkProduct> getAll();

  BulkProduct getEntityFromDto(ProductDto productDto);

}
