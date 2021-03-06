package com.vulpes.velox.services.productservice;

import com.vulpes.velox.dtos.ProductDto;
import com.vulpes.velox.models.Item;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.products.IdentifiedProduct;
import com.vulpes.velox.models.products.Product;
import com.vulpes.velox.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void save(Product product) {
    if (!existsByName(product.getName())) {
      productRepository.save(product);
    }
  }

  @Override
  public void update(Product product) {
    if (existsByName(product.getName())) {
      productRepository.save(product);
    }
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
    BulkProduct bulkProduct =
        (BulkProduct) getByName(bulkProductName);
    bulkProduct.setQuantity(
        bulkProduct.getQuantity() + shipment.getQuantity());
    bulkProduct.setValue(
        BigInteger.valueOf(
            bulkProduct.getValue().intValue() + (shipment.getQuantity() * shipment.getPrice())));
    bulkProduct.setPrice(
        (long) bulkProduct.getValue().intValue() / bulkProduct.getQuantity());
    update(bulkProduct);
  }

  @Override
  public void updateIdentifiedProductWithItem(IdentifiedProduct identifiedProduct,
                                              Item item) {
    identifiedProduct.setQuantity(
        identifiedProduct.getQuantity() + 1);
    identifiedProduct.setValue(BigInteger.valueOf(
        identifiedProduct.getValue().intValue() + item.getPrice()));
    identifiedProduct.setPrice(
        identifiedProduct.getValue().intValue() / identifiedProduct.getQuantity());
    update(identifiedProduct);
  }


}
