package com.vulpes.velox.models.products;

import com.vulpes.velox.models.Item;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class IdentifiedProduct extends Product {

  @OneToMany(mappedBy = "identifiedProduct", cascade = CascadeType.REMOVE)
  private List<Item> items;

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}
