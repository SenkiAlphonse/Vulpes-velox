package com.vulpes.velox.services.shipmentservice;

import com.vulpes.velox.models.products.BulkProduct;
import com.vulpes.velox.models.Shipment;
import com.vulpes.velox.repositories.ShipmentRepository;
import com.vulpes.velox.services.bulkproductservice.BulkProductService;
import com.vulpes.velox.services.methodservice.MethodService;
import com.vulpes.velox.services.productservice.ProductService;
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
  private ProductService productService;
  private MethodService methodService;


  @Autowired
  public ShipmentServiceImpl(ShipmentRepository shipmentRepository,
                             BulkProductService bulkProductService,
                             ProductService productService,
                             MethodService methodService) {
    this.shipmentRepository = shipmentRepository;
    this.bulkProductService = bulkProductService;
    this.productService = productService;
    this.methodService = methodService;
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
  public Map<String, ?> getErrorFlashAttributes(String bulkProductName,
                                                String arrivalDate,
                                                String bestBeforeDate,
                                                Shipment shipment,
                                                RedirectAttributes redirectAttributes) {
    if (shipment.getQuantity() == null) {
      return methodService.getErrorMessageFlashAttributes(
          "Enter quantity.",
          redirectAttributes,
          "shipmentError");
    }
    if (shipment.getQuantity() <= 0) {
      return methodService.getErrorMessageFlashAttributes(
          "Quantity not allowed.",
          redirectAttributes,
          "shipmentError");
    }
    if (!isAllowedDateFormat(arrivalDate) || !isAllowedDateFormat(bestBeforeDate)) {
      return methodService.getErrorMessageFlashAttributes(
          "Date entered not allowed.",
          redirectAttributes,
          "shipmentError");
    }
    if (bulkProductName == null) {
      return methodService.getErrorMessageFlashAttributes(
          "Enter bulk product name.",
          redirectAttributes,
          "shipmentError");
    }
    if (bulkProductName.isEmpty()) {
      return methodService.getErrorMessageFlashAttributes(
          "Empty bulk product name.",
          redirectAttributes,
          "shipmentError");
    }
    if (!bulkProductService.existsByName(bulkProductName)) {
      return methodService.getErrorMessageFlashAttributes(
          "Bulk product not found.",
          redirectAttributes,
          "shipmentError");
    }
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public Map<String, ?> getNewShipmentFlashAttributes(Shipment shipment, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("savedShipment", true);
    redirectAttributes.addFlashAttribute(
        "bulkProductName",
        shipment.getBulkProduct().getName());
    redirectAttributes.addFlashAttribute("quantity", shipment.getQuantity());
    redirectAttributes.addFlashAttribute("arrival", shipment.getArrival());
    redirectAttributes.addFlashAttribute("bestBefore", shipment.getBestBefore());
    return redirectAttributes.getFlashAttributes();
  }

  @Override
  public void saveNewShipment(String bulkProductName,
                              String arrival,
                              String bestBefore,
                              Shipment shipment) {
    shipment.setArrival(
        getLocalDateFromDateString(arrival));
    shipment.setBestBefore(
        getLocalDateFromDateString(bestBefore));
    shipment.setBulkProduct(
        (BulkProduct) productService.getByName(bulkProductName));
    save(shipment);
  }

}
