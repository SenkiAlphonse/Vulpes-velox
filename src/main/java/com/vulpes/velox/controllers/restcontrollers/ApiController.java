package com.vulpes.velox.controllers.restcontrollers;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.exceptions.runtimeexceptions.InternalServerErrorException;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.identifiedproductservice.IdentifiedProductService;
import com.vulpes.velox.services.productservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ApiController {


  private ProductService productService;
  private IdentifiedProductService identifiedProductService;
  private BulkProductService bulkProductService;

  @Autowired
  public ApiController(ProductService productService, IdentifiedProductService identifiedProductService, BulkProductService bulkProductService) {
    this.productService = productService;
    this.identifiedProductService = identifiedProductService;
    this.bulkProductService = bulkProductService;
  }


  @GetMapping("/api/products")
  public ResponseEntity<List<ProductDto>> products() {
    List<ProductDto> productDtos = productService.getDtosFromEntities(productService.getAll());
    if (productDtos != null && !productDtos.isEmpty()) {
      return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
    throw new InternalServerErrorException("Can't find products");
  }

  @PostMapping("/api/product/identified")
  public ResponseEntity newIdentifiedProduct(@Valid @RequestBody ProductDto productDto) {
    productService.save(identifiedProductService.getEntityFromDto(productDto));
    return new ResponseEntity(HttpStatus.OK);
  }

  @PostMapping("/api/product/bulk")
  public ResponseEntity newBulkProduct(@Valid @RequestBody ProductDto productDto) {
    productService.save(bulkProductService.getEntityFromDto(productDto));
    return new ResponseEntity(HttpStatus.OK);
  }


}
