package com.vulpes.velox.services;

import com.vulpes.velox.models.Product;
import com.vulpes.velox.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

  private ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void save(Product product) {
    productRepository.save(product);
  }

  @Override
  public void deleteAll() {
    productRepository.deleteAll();
  }
}
