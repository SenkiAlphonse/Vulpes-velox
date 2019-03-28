package com.vulpes.velox.services.itemservice;

import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

public interface ItemService {

  void save(Item item);

  List<Item> getAll();

  List<Item> getAllByIdentifiedProduct(IdentifiedProduct identifiedProduct);

  boolean existsByProductNumber(Long productNumber);

  Map<String, ?> getErrorFlashAttributes(String identifiedProductName, Item item, RedirectAttributes redirectAttributes);

  Map<String, ?> getNewItemFlashAttributes(Item item, RedirectAttributes redirectAttributes);
}
