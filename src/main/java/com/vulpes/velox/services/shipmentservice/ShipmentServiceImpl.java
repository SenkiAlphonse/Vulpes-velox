package com.vulpes.velox.services.shipmentservice;

import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.repositories.ShipmentRepository;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {
  private ShipmentRepository shipmentRepository;
  private BulkProductService bulkProductService;

  @Autowired
  public ShipmentServiceImpl(ShipmentRepository shipmentRepository, BulkProductService bulkProductService) {
    this.shipmentRepository = shipmentRepository;
    this.bulkProductService = bulkProductService;
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
    return date.length() == 10;
  }

  @Override
  public boolean isShipmentAllowed(String bulkProductName,
                                   String arrival,
                                   String bestBefore,
                                   Shipment shipment) {
    if (bulkProductName != null) {
      if (bulkProductName.isEmpty() || !bulkProductService.existsByName(bulkProductName)) {
        return false;
      }
    }

    if (shipment.getQuantity() != null) {
      if (shipment.getQuantity() == 0) {
        return false;
      }
    }

    return bulkProductName != null &&
        isAllowedDateFormat(arrival) &&
        isAllowedDateFormat(bestBefore) &&
        shipment.getQuantity() != null;
  }


}
