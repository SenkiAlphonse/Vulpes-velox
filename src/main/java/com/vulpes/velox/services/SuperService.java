package com.vulpes.velox.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperService {

  public BulkProductService bulkProductService;

  public IdentifiedProductService identifiedProductService;

  public ItemService itemService;

  public ProductService productService;

  public ShipmentService shipmentService;

  public OrderService orderService;

  public OrderedProductService orderedProductService;

  public UserService userService;

  @Autowired
  public SuperService(BulkProductService bulkProductService,
                      IdentifiedProductService identifiedProductService,
                      ItemService itemService,
                      ProductService productService,
                      ShipmentService shipmentService,
                      OrderService orderService,
                      OrderedProductService orderedProductService,
                      UserService userService) {
    this.bulkProductService = bulkProductService;
    this.identifiedProductService = identifiedProductService;
    this.itemService = itemService;
    this.productService = productService;
    this.shipmentService = shipmentService;
    this.orderService = orderService;
    this.orderedProductService = orderedProductService;
    this.userService = userService;
  }
}
