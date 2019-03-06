package com.vulpes.velox.services;

import com.vulpes.velox.models.IdentifiedProduct;
import com.vulpes.velox.repositories.IdentifiedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentifiedProductServiceImpl implements IdentifiedProductService {

  private IdentifiedProductRepository identifiedProductRepository;

  @Autowired
  public IdentifiedProductServiceImpl(IdentifiedProductRepository identifiedProductRepository) {
    this.identifiedProductRepository = identifiedProductRepository;
  }

  @Override
  public List<IdentifiedProduct> getAll() {
    return identifiedProductRepository.findAll();
  }

}
