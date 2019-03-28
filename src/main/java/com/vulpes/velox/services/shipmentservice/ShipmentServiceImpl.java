package com.vulpes.velox.services.shipmentservice;

import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.repositories.ShipmentRepository;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
  public Map<String, ?> getErrorFlashAttributes(String bulkProductName, String arrivalDate, String bestBeforeDate, Shipment shipment, RedirectAttributes redirectAttributes) {
    if(shipment.getQuantity() == null) {
      return getErrorMessageFlashAttributes("Enter quantity.", redirectAttributes);
    }
    if(shipment.getQuantity() <= 0) {
      return getErrorMessageFlashAttributes("Quantity not allowed.", redirectAttributes);
    }
    if(!isAllowedDateFormat(arrivalDate) || !isAllowedDateFormat(bestBeforeDate)) {
      return getErrorMessageFlashAttributes("Date entered not allowed.", redirectAttributes);
    }
    if(bulkProductName == null) {
      return getErrorMessageFlashAttributes("Enter bulk product name.", redirectAttributes);
    }
    if(bulkProductName.isEmpty()) {
      return getErrorMessageFlashAttributes("Empty bulk product name.", redirectAttributes);
    }
    if(!bulkProductService.existsByName(bulkProductName)) {
      return getErrorMessageFlashAttributes("Bulk product not found.", redirectAttributes);
    }
    return redirectAttributes.getFlashAttributes();
  }

  private Map<String, ?> getErrorMessageFlashAttributes(String message,
                                                        RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("shipmentError", true);
    redirectAttributes.addFlashAttribute("errorMessage", message);
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public Map<String, ?> getNewShipmentFlashAttributes(Shipment shipment, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("savedShipment", true);
    redirectAttributes.addFlashAttribute("bulkProductName", shipment.getBulkProduct().getName());
    redirectAttributes.addFlashAttribute("quantity", shipment.getQuantity());
    redirectAttributes.addFlashAttribute("arrival", shipment.getArrival());
    redirectAttributes.addFlashAttribute("bestBefore", shipment.getBestBefore());
    return redirectAttributes.getFlashAttributes();
  }

}
