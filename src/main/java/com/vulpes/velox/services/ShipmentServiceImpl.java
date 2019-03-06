package com.vulpes.velox.services;

import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class ShipmentServiceImpl implements ShipmentService {
  private ShipmentRepository shipmentRepository;

  @Autowired
  public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
    this.shipmentRepository = shipmentRepository;
  }

  @Override
  public void save(Shipment shipment) {
    shipmentRepository.save(shipment);
  }

  @Override
  public Instant getInstantFromDateString(String date) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

  }
}
