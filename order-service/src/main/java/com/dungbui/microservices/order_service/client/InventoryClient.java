package com.dungbui.microservices.order_service.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import groovy.util.logging.Slf4j;

@Slf4j
public interface InventoryClient {
    
    @GetExchange("/api/inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}
