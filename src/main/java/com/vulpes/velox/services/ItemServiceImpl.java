package com.vulpes.velox.services;

import com.vulpes.velox.models.Item;
import com.vulpes.velox.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService{

  private ItemRepository itemRepository;

  @Autowired
  public ItemServiceImpl(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @Override
  public void save(Item item) {
    itemRepository.save(item);
  }
}
