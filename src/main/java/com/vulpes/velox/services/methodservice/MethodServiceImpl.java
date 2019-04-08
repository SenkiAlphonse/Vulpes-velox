package com.vulpes.velox.services.methodservice;

import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.Product;
import com.vulpes.velox.services.productservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Service
public class MethodServiceImpl implements MethodService {

  private ProductService productService;

  @Autowired
  public MethodServiceImpl(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public Map<String, ?> getErrorMessageFlashAttributes(String message,
                                                       RedirectAttributes redirectAttributes,
                                                       String errorAttribute) {
    if (message.isEmpty()) {
      return redirectAttributes.getFlashAttributes();
    }

    redirectAttributes.addFlashAttribute(errorAttribute, true);
    redirectAttributes.addFlashAttribute("errorMessage", message);
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public Map<String, ?> getNameErrorAttributes(Product product,
                                               RedirectAttributes redirectAttributes,
                                               String errorAttribute) {
    String displayName;
    if (product instanceof BulkProduct) {
      displayName = "bulk product";
    } else {
      displayName = "identified product";
    }

    if (product.getName() == null) {
      return getErrorMessageFlashAttributes(
          "Enter " + displayName + " name.",
          redirectAttributes,
          errorAttribute);
    }
    if (product.getName().isEmpty()) {
      return getErrorMessageFlashAttributes(
          "Empty " + displayName + " name.",
          redirectAttributes,
          errorAttribute);
    }
    if (productService.existsByName(product.getName())) {
      return getErrorMessageFlashAttributes(
          "Product name already exists.",
          redirectAttributes,
          errorAttribute);
    }
    return redirectAttributes.getFlashAttributes();
  }

}
