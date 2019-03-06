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
  @JoinColumn(name = "product_without_id_id")
  private BulkProduct bulkProduct;
}
