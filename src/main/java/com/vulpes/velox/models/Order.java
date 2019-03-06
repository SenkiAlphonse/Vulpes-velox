package com.vulpes.velox.models;

import com.vulpes.velox.dtos.BulkProductOrderDto;
import com.vulpes.velox.dtos.IdentifiedProductOrderDto;

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

  private List<BulkProductOrderDto> bulkProductOrderDtos;
  private List<IdentifiedProductOrderDto> identifiedProductOrderDtos;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<BulkProductOrderDto> getBulkProductOrderDtos() {
    return bulkProductOrderDtos;
  }

  public void setBulkProductOrderDtos(List<BulkProductOrderDto> bulkProductOrderDtos) {
    this.bulkProductOrderDtos = bulkProductOrderDtos;
  }

  public List<IdentifiedProductOrderDto> getIdentifiedProductOrderDtos() {
    return identifiedProductOrderDtos;
  }

  public void setIdentifiedProductOrderDtos(List<IdentifiedProductOrderDto> identifiedProductOrderDtos) {
    this.identifiedProductOrderDtos = identifiedProductOrderDtos;
  }
}
