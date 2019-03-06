package com.vulpes.velox.services;

import com.vulpes.velox.models.OrderedProduct;
import com.vulpes.velox.repositories.OrderedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderedProductServiceImpl implements OrderedProductService{

  private OrderedProductRepository orderedProductRepository;

  @Autowired
  public OrderedProductServiceImpl(OrderedProductRepository orderedProductRepository) {
    this.orderedProductRepository = orderedProductRepository;
  }

  @Override
  public void save(OrderedProduct orderedProduct) {
    orderedProductRepository.save(orderedProduct);
  }
}
