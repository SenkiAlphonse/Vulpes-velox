package com.vulpes.velox.models;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "bulk_product_shipments")
public class BulkProductShipment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long quantity;
  private Instant arrival;
  @Column(name = "best_before")
  private Instant bestBefore;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bulk_product_id")
  private BulkProduct bulkProduct;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Instant getArrival() {
    return arrival;
  }

  public void setArrival(Instant arrival) {
    this.arrival = arrival;
  }

  public Instant getBestBefore() {
    return bestBefore;
  }

  public void setBestBefore(Instant bestBefore) {
    this.bestBefore = bestBefore;
  }

  public BulkProduct getBulkProduct() {
    return bulkProduct;
  }

  public void setBulkProduct(BulkProduct bulkProduct) {
    this.bulkProduct = bulkProduct;
  }
}
