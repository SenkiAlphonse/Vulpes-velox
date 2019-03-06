package com.vulpes.velox.services;

import com.vulpes.velox.models.Shipment;

import java.time.LocalDate;
import java.util.List;

public interface ShipmentService {

  void save(Shipment shipment);

  LocalDate getLocalDateFromDateString(String date);

  List<Shipment> getAll();

}
