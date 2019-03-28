package com.vulpes.velox.services.itemservice;

import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.Item;

import java.util.List;

public interface ItemService {

  void save(Item item);

  List<Item> getAll();

  List<Item> getAllByIdentifiedProduct(IdentifiedProduct identifiedProduct);

}
