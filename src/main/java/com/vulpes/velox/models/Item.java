package com.vulpes.velox.models;

import com.vulpes.velox.models.products.IdentifiedProduct;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "product_number")
  private Long productNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "identified_product_id")
  private IdentifiedProduct identifiedProduct;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductNumber() {
    return productNumber;
  }

  public void setProductNumber(Long productNumber) {
    this.productNumber = productNumber;
  }

  public IdentifiedProduct getIdentifiedProduct() {
    return identifiedProduct;
  }

  public void setIdentifiedProduct(IdentifiedProduct identifiedProduct) {
    this.identifiedProduct = identifiedProduct;
  }
}
