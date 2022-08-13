package com.ecom.controller;

import com.ecom.payload.ApiResponse;
import com.ecom.payload.OrderDto;
import com.ecom.payload.OrderRequest;
import com.ecom.payload.ProductDto;
import com.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //for create order
    @PostMapping("/orders")
    public ResponseEntity<ApiResponse> createOrder(
            @RequestBody OrderRequest orderRequest,
            Principal principal
    ) {
        //let suppose order is create success
        String username = principal.getName();
        this.orderService.createOrder(orderRequest, username);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Order is created successfully !!");
        apiResponse.setStatus(HttpStatus.OK);
        return ResponseEntity.ok(apiResponse);
    }

    // get orders

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = this.orderService.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    //single order:by id

    @GetMapping("/orders/{orderId}/")
    public ResponseEntity<OrderDto> getById(@PathVariable Integer orderId) {
    	OrderDto order=this.orderService.getById(orderId);
        return new ResponseEntity<OrderDto>( order,HttpStatus.OK);
    }



    // delete orders

    @DeleteMapping("/orders/{orderId}")
    public ApiResponse delete(@PathVariable Integer orderId)
       {
       	this.orderService.delete(orderId);
       	return new ApiResponse("order successfully deleted",true, HttpStatus.OK);
       }

}
