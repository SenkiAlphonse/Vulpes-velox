package com.vulpes.velox.services;

import com.vulpes.velox.models.Order;
import com.vulpes.velox.models.OrderedProduct;
import com.vulpes.velox.repositories.OrderedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

  @Override
  public List<OrderedProduct> getAll() {
    return orderedProductRepository.findAll();
  }

  @Override
  public List<OrderedProduct> getAllByOrder(Order order) {
    return orderedProductRepository.findAllByOrder(order);
  }

  @Override
  public List<OrderedProduct> getAllByProductName(String productName) {
    return orderedProductRepository.findAllByProductName(productName);
  }
}
