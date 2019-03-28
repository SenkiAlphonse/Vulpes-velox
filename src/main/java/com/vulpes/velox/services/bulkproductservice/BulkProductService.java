package com.vulpes.velox.services.bulkproductservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.Product;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

public interface BulkProductService {

  List<BulkProduct> getAll();

  BulkProduct getEntityFromDto(ProductDto productDto);

  boolean existsByName(String name);

  Map<String, ?> getErrorFlashAttributes(BulkProduct bulkProduct, RedirectAttributes redirectAttributes);

  Map<String, ?> getNewBulkProductFlashAttributes(BulkProduct bulkProduct, RedirectAttributes redirectAttributes);

}
