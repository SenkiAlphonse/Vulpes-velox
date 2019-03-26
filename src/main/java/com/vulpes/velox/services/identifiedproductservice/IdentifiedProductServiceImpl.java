package com.vulpes.velox.services.identifiedproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.repositories.IdentifiedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentifiedProductServiceImpl implements IdentifiedProductService {

  private IdentifiedProductRepository identifiedProductRepository;

  @Autowired
  public IdentifiedProductServiceImpl(IdentifiedProductRepository identifiedProductRepository) {
    this.identifiedProductRepository = identifiedProductRepository;
  }

  @Override
  public List<IdentifiedProduct> getAll() {
    return identifiedProductRepository.findAll();
  }

  @Override
  public IdentifiedProduct getEntityFromDto(ProductDto productDto) {
    IdentifiedProduct identifiedProduct = new IdentifiedProduct();
    identifiedProduct.setName(productDto.name);
    identifiedProduct.setQuantity(productDto.quantity);
    return identifiedProduct;
  }

}
