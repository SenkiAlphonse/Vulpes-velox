package com.vulpes.velox.services.shipmentservice;

import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.Shipment;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ShipmentService {

  void save(Shipment shipment);

  LocalDate getLocalDateFromDateString(String date);

  List<Shipment> getAll();

  List<Shipment> getAllByBulkProduct(BulkProduct bulkProduct);

  boolean isAllowedDateFormat(String date);

  Map<String, ?> getErrorFlashAttributes(String bulkProductName,
                                         String arrivalDate,
                                         String bestBeforeDate,
                                         Shipment shipment,
                                         RedirectAttributes redirectAttributes);

  Map<String, ?> getNewShipmentFlashAttributes(Shipment shipment, RedirectAttributes redirectAttributes);

  void saveNewShipment(String bulkProductName, String arrival, String bestBefore, Shipment shipment);

}
