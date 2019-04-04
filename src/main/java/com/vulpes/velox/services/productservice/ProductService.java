package com.vulpes.velox.services.productservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.products.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

  void save(Product product);

  void update(Product product);

  void deleteAll();

  boolean existsByName(String name);

  Product getByName(String name);

  List<ProductDto> getDtosFromEntities(List<Product> products);

  ProductDto getDtoFromEntity(Product product);

  List<Product> getAll();

  void updateBulkProductWithShipment(String bulkProductName, Shipment shipment);

  void updateIdentifiedProductWithItem(IdentifiedProduct identifiedProduct, Item item);



}

