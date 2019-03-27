package com.vulpes.velox.services.bulkproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.repositories.BulkProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulkProductServiceImpl implements BulkProductService {

  private BulkProductRepository bulkProductRepository;

  @Autowired
  public BulkProductServiceImpl(BulkProductRepository bulkProductRepository) {
    this.bulkProductRepository = bulkProductRepository;
  }

  @Override
  public List<BulkProduct> getAll() {
    return bulkProductRepository.findAll();
  }

  @Override
  public BulkProduct getEntityFromDto(ProductDto productDto) {
    BulkProduct bulkProduct = new BulkProduct();
    bulkProduct.setName(productDto.name);
    bulkProduct.setQuantity(productDto.quantity);
    return bulkProduct;
  }

  @Override
  public boolean existsByName(String name) {
    return bulkProductRepository.findByName(name) != null;
  }

  @Override
  public BulkProduct getByName(String name) {
    return bulkProductRepository.findByName(name);
  }

}
