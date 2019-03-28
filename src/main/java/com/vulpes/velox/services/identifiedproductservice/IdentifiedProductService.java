package com.vulpes.velox.services.identifiedproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.IdentifiedProduct;

import java.util.List;

public interface IdentifiedProductService {

  List<IdentifiedProduct> getAll();

  IdentifiedProduct getEntityFromDto(ProductDto productDto);

}
