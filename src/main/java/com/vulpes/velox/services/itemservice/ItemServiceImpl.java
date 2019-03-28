package com.vulpes.velox.services.itemservice;

import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService{

  private ItemRepository itemRepository;

  @Autowired
  public ItemServiceImpl(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
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
  public Map<String, ?> getErrorFlashAttributes(String identifiedProductName, Item item, RedirectAttributes redirectAttributes) {
    if(item.getProductNumber() == null) {
      return getErrorMessageFlashAttributes("Enter product number.", redirectAttributes);
    }
    if(item.getProductNumber().toString().length() != 8) {
      return getErrorMessageFlashAttributes("Product number has to be 8 digits.", redirectAttributes);
    }
    if(itemRepository.existsByProductNumber(item.getProductNumber())) {
      return getErrorMessageFlashAttributes("Product number already exists.", redirectAttributes);
    }
    if(identifiedProductName == null) {
      return getErrorMessageFlashAttributes("Enter identified product.", redirectAttributes);
    }
    if(identifiedProductName.isEmpty()) {
      return getErrorMessageFlashAttributes("Empty identified product name.", redirectAttributes);
    }
    return redirectAttributes.getFlashAttributes();
  }

  private Map<String, ?> getErrorMessageFlashAttributes(String message,
                                                        RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("itemError", true);
    redirectAttributes.addFlashAttribute("errorMessage", message);
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public Map<String, ?> getNewItemFlashAttributes(Item item, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("savedItem", true);
    redirectAttributes.addFlashAttribute("productNumber", item.getProductNumber());
    redirectAttributes.addFlashAttribute("identifiedProductName", item.getIdentifiedProduct().getName());
    return redirectAttributes.getFlashAttributes();
  }
}
