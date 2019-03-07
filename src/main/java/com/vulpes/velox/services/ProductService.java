package com.vulpes.velox.services;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.Product;

import java.util.List;

public interface ProductService {

  void save(Product product);

  void deleteAll();

  boolean existsByName(String name);

  Product getByName(String name);

  List<ProductDto> getDtosFromEntities(List<Product> products);

  ProductDto getDtoFromEntity(Product product);

  List<Product> getAll();



}

