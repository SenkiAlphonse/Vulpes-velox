package com.vulpes.velox.services;

import com.vulpes.velox.models.Shipment;

import java.time.Instant;

public interface ShipmentService {

  void save(Shipment shipment);

  Instant getInstantFromDateString(String date);

}
