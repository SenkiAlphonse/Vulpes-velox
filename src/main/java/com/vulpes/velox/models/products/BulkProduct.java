package com.vulpes.velox.models.products;

import com.vulpes.velox.models.Shipment;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigInteger;
import java.util.List;

@Entity
@Audited
@AuditTable("bulk_products_AUD")
public class BulkProduct extends Product  {

  @OneToMany(mappedBy = "bulkProduct", cascade = CascadeType.REMOVE)
  private List<Shipment> shipments;

  public BulkProduct() {
  }

  public BulkProduct(BigInteger value) {
    super(value);
  }

  public List<Shipment> getShipments() {
    return shipments;
  }

  public void setShipments(List<Shipment> shipments) {
    this.shipments = shipments;
  }

  @Override
  public String toString() {
    return "BulkProduct{" +
        "id=" + super.getId() +
        ", name='" + super.getName() + '\'' +
        ", quantity=" + super.getQuantity() +
        '}';
  }
}
