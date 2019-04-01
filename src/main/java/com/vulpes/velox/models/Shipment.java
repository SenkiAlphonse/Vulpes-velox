package com.vulpes.velox.models;

import com.vulpes.velox.models.products.BulkProduct;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shipments")
@Audited
@AuditTable("shipments_AUD")
public class Shipment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long quantity;
  private LocalDate arrival;
  @Column(name = "best_before")
  private LocalDate bestBefore;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bulk_product_id")
  private BulkProduct bulkProduct;

  public Shipment() {
  }

  public Shipment(Long quantity) {
    this.quantity = quantity;
  }

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

  public LocalDate getArrival() {
    return arrival;
  }

  public void setArrival(LocalDate arrival) {
    this.arrival = arrival;
  }

  public LocalDate getBestBefore() {
    return bestBefore;
  }

  public void setBestBefore(LocalDate bestBefore) {
    this.bestBefore = bestBefore;
  }

  public BulkProduct getBulkProduct() {
    return bulkProduct;
  }

  public void setBulkProduct(BulkProduct bulkProduct) {
    this.bulkProduct = bulkProduct;
  }
}
