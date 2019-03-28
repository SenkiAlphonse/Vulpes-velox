package com.vulpes.velox.services.identifiedproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.IdentifiedProduct;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

public interface IdentifiedProductService {

  List<IdentifiedProduct> getAll();

  IdentifiedProduct getEntityFromDto(ProductDto productDto);

  Map<String, ?> getErrorFlashAttributes(IdentifiedProduct identifiedProduct, RedirectAttributes redirectAttributes);

  Map<String, ?> getNewIdentifiedProductFlashAttributes(IdentifiedProduct identifiedProduct, RedirectAttributes redirectAttributes);
}
