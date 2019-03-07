package com.vulpes.velox.services;

import com.vulpes.velox.models.BulkProduct;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
  public LocalDate getLocalDateFromDateString(String date) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, dateTimeFormatter);
  }

  @Override
  public List<Shipment> getAll() {
    return shipmentRepository.findAll();
  }

  @Override
  public List<Shipment> getAllByBulkProduct(BulkProduct bulkProduct) {
    return shipmentRepository.findAllByBulkProduct(bulkProduct);
  }

  @Override
  public boolean isAllowedDateFormat(String date) {
    return date.length() != 10;
  }


}
