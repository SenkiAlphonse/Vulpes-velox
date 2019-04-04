package com.vulpes.velox.services.identifiedproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.repositories.IdentifiedProductRepository;
import com.vulpes.velox.services.methodservice.MethodService;
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
  private MethodService methodService;

  @Autowired
  public IdentifiedProductServiceImpl(IdentifiedProductRepository identifiedProductRepository, ProductService productService, MethodService methodService) {
    this.identifiedProductRepository = identifiedProductRepository;
    this.productService = productService;
    this.methodService = methodService;
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
      return methodService.getErrorMessageFlashAttributes(
          "Enter identified product name.",
          redirectAttributes,
          "identifiedProductError");
    }
    if(identifiedProduct.getName().isEmpty()) {
      return methodService.getErrorMessageFlashAttributes(
          "Empty identified product name.",
          redirectAttributes,
          "identifiedProductError");
    }
    if(productService.existsByName(identifiedProduct.getName())) {
      return methodService.getErrorMessageFlashAttributes(
          "Product name already exists.",
          redirectAttributes,
          "identifiedProductError");
    }
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public Map<String, ?> getNewIdentifiedProductFlashAttributes(IdentifiedProduct identifiedProduct, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("savedIdentifiedProduct", true);
    redirectAttributes.addFlashAttribute("identifiedProductName", identifiedProduct.getName());
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public List<IdentifiedProduct> getAllFilteredBy(String filter) {
    return identifiedProductRepository.findAllByNameContaining(filter);
  }

}
