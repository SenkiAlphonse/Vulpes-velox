package com.vulpes.velox.services.itemservice;

import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.repositories.ItemRepository;
import com.vulpes.velox.services.methodservice.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
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
    String attributeName = "itemError";
    String message = "";
    if (item.getProductNumber() == null) {
      message = "Enter product number.";
    } else if (item.getProductNumber().toString().length() != 8) {
      message = "Product number has to be 8 digits.";
    } else if (existsByProductNumber(item.getProductNumber())) {
      message = "Product number already exists.";
    } else if (identifiedProductName == null) {
      message = "Enter identified product.";
    } else if (identifiedProductName.isEmpty()) {
      message = "Empty identified product name.";
    } else if (item.getPrice() == null) {
      message = "Enter price.";
    } else if (item.getPrice() <= 0) {
      message = "Price not allowed.";
    }
    return methodService.getErrorMessageFlashAttributes(
        message,
        redirectAttributes,
        attributeName);
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
