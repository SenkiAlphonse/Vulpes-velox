package com.vulpes.velox.services.bulkproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.repositories.BulkProductRepository;
import com.vulpes.velox.services.productservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Service
public class BulkProductServiceImpl implements BulkProductService {

  private BulkProductRepository bulkProductRepository;
  private ProductService productService;

  @Autowired
  public BulkProductServiceImpl(BulkProductRepository bulkProductRepository, ProductService productService) {
    this.bulkProductRepository = bulkProductRepository;
    this.productService = productService;
  }

  @Override
  public List<BulkProduct> getAll() {
    return bulkProductRepository.findAll();
  }

  @Override
  public BulkProduct getEntityFromDto(ProductDto productDto) {
    BulkProduct bulkProduct = new BulkProduct();
    bulkProduct.setName(productDto.name);
    bulkProduct.setQuantity(productDto.quantity);
    return bulkProduct;
  }

  @Override
  public boolean existsByName(String name) {
    return bulkProductRepository.existsByName(name);
  }

  @Override
  public Map<String, ?> getErrorFlashAttributes(BulkProduct bulkProduct, RedirectAttributes redirectAttributes) {
    if(bulkProduct.getName() == null) {
      return getErrorMessageFlashAttributes("Enter bulk product name.", redirectAttributes);
    }
    if(bulkProduct.getName().isEmpty()) {
      return getErrorMessageFlashAttributes("Empty bulk product name.", redirectAttributes);
    }
    if(productService.existsByName(bulkProduct.getName())) {
      return getErrorMessageFlashAttributes("Product name already exists.", redirectAttributes);
    }
    return redirectAttributes.getFlashAttributes();
  }

  private Map<String, ?> getErrorMessageFlashAttributes(String message,
                                                        RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("productError", true);
    redirectAttributes.addFlashAttribute("errorMessage", message);
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public Map<String, ?> getNewBulkProductFlashAttributes(BulkProduct bulkProduct, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("savedBulkProduct", true);
    redirectAttributes.addFlashAttribute("bulkProductName", bulkProduct.getName());
    return redirectAttributes.getFlashAttributes();
  }

}
