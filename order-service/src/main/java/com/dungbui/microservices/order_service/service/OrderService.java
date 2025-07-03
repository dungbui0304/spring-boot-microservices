package com.dungbui.microservices.order_service.service;

import java.util.UUID;
import org.springframework.stereotype.Service;

import com.dungbui.microservices.order_service.client.InventoryClient;
import com.dungbui.microservices.order_service.dto.OrderRequest;
import com.dungbui.microservices.order_service.model.Order;
import com.dungbui.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    
    public void placeOrder(OrderRequest request) {
        var isProductInStock = inventoryClient.isInStock(request.skuCode(), request.quantity());
        if (isProductInStock) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setSkuCode(request.skuCode());
        order.setPrice(request.price());
        order.setQuantity(request.quantity());
        orderRepository.save(order);     
        } else {
            throw new RuntimeException("Product with SKU code " + request.skuCode() + " is not in stock.");
        }            
    }
}
