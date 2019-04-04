package com.vulpes.velox.services.itemservice;

import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.repositories.ItemRepository;
import com.vulpes.velox.services.methodservice.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

  private ItemRepository itemRepository;
  private MethodService methodService;

  @Autowired
  public ItemServiceImpl(ItemRepository itemRepository, MethodService methodService) {
    this.itemRepository = itemRepository;
    this.methodService = methodService;
  }

  @Override
  public void save(Item item) {
    itemRepository.save(item);
  }

  @Override
  public List<Item> getAll() {
    return itemRepository.findAll();
  }

  @Override
  public List<Item> getAllByIdentifiedProduct(IdentifiedProduct identifiedProduct) {
    return itemRepository.findAllByIdentifiedProduct(identifiedProduct);
  }

  @Override
  public boolean existsByProductNumber(Long productNumber) {
    return itemRepository.existsByProductNumber(productNumber);
  }

  @Override
  public Map<String, ?> getErrorFlashAttributes(String identifiedProductName,
                                                Item item,
                                                RedirectAttributes redirectAttributes) {
    if (item.getProductNumber() == null) {
      return methodService.getErrorMessageFlashAttributes(
          "Enter product number.",
          redirectAttributes,
          "itemError");
    }
    if (item.getProductNumber().toString().length() != 8) {
      return methodService.getErrorMessageFlashAttributes(
          "Product number has to be 8 digits.",
          redirectAttributes,
          "itemError");
    }
    if (itemRepository.existsByProductNumber(item.getProductNumber())) {
      return methodService.getErrorMessageFlashAttributes(
          "Product number already exists.",
          redirectAttributes,
          "itemError");
    }
    if (identifiedProductName == null) {
      return methodService.getErrorMessageFlashAttributes(
          "Enter identified product.",
          redirectAttributes,
          "itemError");
    }
    if (identifiedProductName.isEmpty()) {
      return methodService.getErrorMessageFlashAttributes(
          "Empty identified product name.",
          redirectAttributes,
          "itemError");
    }
    if (item.getPrice() == null) {
      return methodService.getErrorMessageFlashAttributes(
          "Enter price.",
          redirectAttributes,
          "itemError");
    }
    if (item.getPrice() <= 0) {
      return methodService.getErrorMessageFlashAttributes(
          "Price not allowed.",
          redirectAttributes,
          "itemError");
    }
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public Map<String, ?> getNewItemFlashAttributes(Item item,
                                                  RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("savedItem", true);
    redirectAttributes.addFlashAttribute("productNumber", item.getProductNumber());
    redirectAttributes.addFlashAttribute(
        "identifiedProductName",
        item.getIdentifiedProduct().getName());
    return redirectAttributes.getFlashAttributes();
  }
}
