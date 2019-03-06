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
    if(!existsByName(product.getName())) {
      productRepository.save(product);
    }
  }

  @Override
  public void deleteAll() {
    productRepository.deleteAll();
  }

  @Override
  public boolean existsByName(String name) {
    return productRepository.existsByName(name);
  }

  @Override
  public Product getByName(String name) {
    return productRepository.findByName(name);
  }
}
