package com.vulpes.velox.services.methodservice;

import com.vulpes.velox.models.products.Product;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

public interface MethodService {

  Map<String, ?> getErrorMessageFlashAttributes(String message,
                                                RedirectAttributes redirectAttributes,
                                                String errorAttribute);

  Map<String, ?> getNameErrorAttributes(Product product, RedirectAttributes redirectAttributes);
}


