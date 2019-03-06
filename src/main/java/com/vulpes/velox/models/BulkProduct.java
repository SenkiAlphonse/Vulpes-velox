package com.vulpes.velox.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "bulk_products")
public class BulkProduct extends Product  {

  @OneToMany(mappedBy = "bulkProduct", cascade = CascadeType.REMOVE)
  private List<BulkProductShipment> bulkProductShipments;

  public List<BulkProductShipment> getBulkProductShipments() {
    return bulkProductShipments;
  }

  public void setBulkProductShipments(List<BulkProductShipment> bulkProductShipments) {
    this.bulkProductShipments = bulkProductShipments;
  }
}
