package com.vulpes.velox.services.productservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.Product;
import com.vulpes.velox.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService{

  private ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void save(Product product) {
    if(!existsByName(product.getName())) {
      productRepository.save(product);
    }
  }

  @Override
  public void update(Product product) {
    productRepository.save(product);
  }

  @Override
  public void deleteAll() {
    productRepository.deleteAll();
  }

  @Override
  public boolean existsByName(String name) {
    return productRepository.existsByName(name);
  }

  @Override
  public Product getByName(String name) {
    return productRepository.findByName(name);
  }

  @Override
  public List<ProductDto> getDtosFromEntities(List<Product> products) {
    if (products == null || products.isEmpty()) {
      return Collections.emptyList();
    }
    List<ProductDto> productDtos = new ArrayList<>();

    for (Product product : products) {
      productDtos.add(getDtoFromEntity(product));
    }
    return productDtos;
  }

  @Override
  public ProductDto getDtoFromEntity(Product product) {
    ProductDto productDto = new ProductDto();
    productDto.name = product.getName();
    productDto.quantity = product.getQuantity();
    return productDto;
  }

  @Override
  public List<Product> getAll() {
    return productRepository.findAll();
  }

  @Override
  public void updateBulkProductWithShipment(String bulkProductName, Shipment shipment) {
    BulkProduct bulkProduct = (BulkProduct) getByName(bulkProductName);
    bulkProduct.setQuantity(bulkProduct.getQuantity() + shipment.getQuantity());
    bulkProduct.setPrice(bulkProduct.getPrice() + shipment.getQuantity() * shipment.getPrice());
    update(bulkProduct);
  }


}
