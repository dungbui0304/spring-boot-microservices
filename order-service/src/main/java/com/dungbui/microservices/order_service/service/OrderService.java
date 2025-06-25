package com.dungbui.microservices.order_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import com.dungbui.microservices.order_service.dto.OrderRequest;
import com.dungbui.microservices.order_service.model.Order;
import com.dungbui.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest request) {
        // map request to Order entity
        // save Order entity to the database
        // call inventory service to reduce the stock
        // call payment service to charge the customer
        // for simplicity, we will just print the order details
        System.out.println("Order placed: " + request);
        // In a real application, you would handle the business logic here
        // and interact with the database and other services.
        // For example:
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setSkuCode(request.skuCode());
        order.setPrice(request.price());
        order.setQuantity(request.quantity());
        orderRepository.save(order);                 
    }
}
