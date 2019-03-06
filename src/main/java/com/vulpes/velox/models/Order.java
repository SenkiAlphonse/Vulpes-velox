package com.vulpes.velox.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Date date;

  @OneToMany(mappedBy = "order")
  private List<ProductForOrder> productForOrders;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<ProductForOrder> getProductForOrders() {
    return productForOrders;
  }

  public void setProductForOrders(List<ProductForOrder> productForOrders) {
    this.productForOrders = productForOrders;
  }
}
