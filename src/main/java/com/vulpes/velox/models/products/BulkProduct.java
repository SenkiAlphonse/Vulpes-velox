package com.vulpes.velox.models.products;

import com.vulpes.velox.models.Shipment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class BulkProduct extends Product  {

  @OneToMany(mappedBy = "bulkProduct", cascade = CascadeType.REMOVE)
  private List<Shipment> shipments;

  public List<Shipment> getShipments() {
    return shipments;
  }

  public void setShipments(List<Shipment> shipments) {
    this.shipments = shipments;
  }
}
