package com.vulpes.velox.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "identified_products")
public class IdentifiedProduct extends Product {

  @OneToMany(mappedBy = "identifiedProduct", cascade = CascadeType.REMOVE)
  private List<IdentifiedProductItem> identifiedProductItems;

  public List<IdentifiedProductItem> getIdentifiedProductItems() {
    return identifiedProductItems;
  }

  public void setIdentifiedProductItems(List<IdentifiedProductItem> identifiedProductItems) {
    this.identifiedProductItems = identifiedProductItems;
  }
}
