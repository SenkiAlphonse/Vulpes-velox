package com.vulpes.velox.services.identifiedproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.repositories.IdentifiedProductRepository;
import com.vulpes.velox.services.methodservice.MethodService;
import com.vulpes.velox.services.productservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class IdentifiedProductServiceImpl implements IdentifiedProductService {

  private IdentifiedProductRepository identifiedProductRepository;
  private ProductService productService;
  private MethodService methodService;

  @Autowired
  public IdentifiedProductServiceImpl(IdentifiedProductRepository identifiedProductRepository,
                                      ProductService productService,
                                      MethodService methodService) {
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
  public Map<String, ?> getErrorFlashAttributes(IdentifiedProduct identifiedProduct,
                                                RedirectAttributes redirectAttributes) {
    return methodService.getNameErrorAttributes(
        identifiedProduct,
        redirectAttributes,
        "identifiedProductError");
  }

  @Override
  public Map<String, ?> getNewIdentifiedProductFlashAttributes(
      IdentifiedProduct identifiedProduct,
      RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("savedIdentifiedProduct", true);
    redirectAttributes.addFlashAttribute("identifiedProductName", identifiedProduct.getName());
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public List<IdentifiedProduct> getAllFilteredBy(String filter) {
    return identifiedProductRepository.findAllByNameContaining(filter);
  }

  @Override
  public void saveNewIdentifiedProduct(IdentifiedProduct identifiedProduct) {
    identifiedProduct.setQuantity((long) 0);
    identifiedProduct.setPrice((long) 0);
    identifiedProduct.setValue(BigInteger.valueOf(0));
    identifiedProduct.setUnit("Piece");
    productService.save(identifiedProduct);
  }


}
