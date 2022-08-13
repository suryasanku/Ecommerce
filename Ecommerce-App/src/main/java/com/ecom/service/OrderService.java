package com.ecom.service;

import com.ecom.payload.OrderDto;
import com.ecom.payload.OrderRequest;
import com.ecom.payload.ProductDto;

import java.util.List;

public interface OrderService {

    void createOrder(OrderRequest request,String username);


    List<OrderDto> getOrders();
    
    void delete(Integer orderId);
    
    OrderDto getById(Integer orderId);
}
