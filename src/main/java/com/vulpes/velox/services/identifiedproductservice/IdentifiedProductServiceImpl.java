package com.vulpes.velox.services.identifiedproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.repositories.IdentifiedProductRepository;
import com.vulpes.velox.services.productservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Service
public class IdentifiedProductServiceImpl implements IdentifiedProductService {

  private IdentifiedProductRepository identifiedProductRepository;
  private ProductService productService;

  @Autowired
  public IdentifiedProductServiceImpl(IdentifiedProductRepository identifiedProductRepository, ProductService productService) {
    this.identifiedProductRepository = identifiedProductRepository;
    this.productService = productService;
  }

  @Override
  public List<IdentifiedProduct> getAll() {
    return identifiedProductRepository.findAll();
  }

  @Override
  public IdentifiedProduct getEntityFromDto(ProductDto productDto) {
    IdentifiedProduct identifiedProduct = new IdentifiedProduct();
    identifiedProduct.setName(productDto.name);
    identifiedProduct.setQuantity(productDto.quantity);
    return identifiedProduct;
  }

  @Override
  public Map<String, ?> getErrorFlashAttributes(IdentifiedProduct identifiedProduct, RedirectAttributes redirectAttributes) {
    if(identifiedProduct.getName() == null) {
      return getErrorMessageFlashAttributes("Enter identified product name.", redirectAttributes);
    }
    if(identifiedProduct.getName().isEmpty()) {
      return getErrorMessageFlashAttributes("Empty identified product name.", redirectAttributes);
    }
    if(productService.existsByName(identifiedProduct.getName())) {
      return getErrorMessageFlashAttributes("Product name already exists.", redirectAttributes);
    }
    return redirectAttributes.getFlashAttributes();
  }

  private Map<String, ?> getErrorMessageFlashAttributes(String message,
                                                        RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("identifiedProductError", true);
    redirectAttributes.addFlashAttribute("errorMessage", message);
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public Map<String, ?> getNewIdentifiedProductFlashAttributes(IdentifiedProduct identifiedProduct, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("savedIdentifiedProduct", true);
    redirectAttributes.addFlashAttribute("identifiedProductName", identifiedProduct.getName());
    return redirectAttributes.getFlashAttributes();
  }

}
