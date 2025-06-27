package com.dungbui.microservices.inventory.service;

import org.springframework.stereotype.Service;
import com.dungbui.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    
    public boolean isInStock(String skuCode, Integer quantity) {
        // find the inventory for a given skuCode where quantity is greater than 0
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }
}
