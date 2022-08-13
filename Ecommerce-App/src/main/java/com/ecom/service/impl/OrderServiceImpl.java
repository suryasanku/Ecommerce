package com.ecom.service.impl;

import com.ecom.entity.Cart;
import com.ecom.entity.Order;
import com.ecom.entity.OrderItem;
import com.ecom.entity.Product;
import com.ecom.entity.User;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.payload.OrderDto;
import com.ecom.payload.OrderRequest;
import com.ecom.payload.ProductDto;
import com.ecom.repository.CartRepo;
import com.ecom.repository.OrderRepo;
import com.ecom.repository.UserRepository;
import com.ecom.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepo cartRepo;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createOrder(OrderRequest request, String username) {

        //actual order create

        Cart cart = this.cartRepo.findById(request.getCartId()).orElseThrow(() -> new ResourceNotFoundException("Cart", "cart id ", request.getCartId() + ""));

        Order order = new Order();
        Set<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> new OrderItem(cartItem.getProduct(), cartItem.getQuantity(), order)).collect(Collectors.toSet());

        order.setItems(orderItems);
        order.setBillingAddress(request.getAddress());
        order.setOrderCreated(new Date());
        order.setOrderStatus("PENDING");
        order.setPaymentStatus("NOT PAID");
        order.setOrderDelivered(null);

        final double[] totalPrice = {0};
        cart.getItems().forEach(cartItem -> {
            totalPrice[0] = totalPrice[0] + cartItem.getTotalPrice();
        });

        order.setTotalAmount(totalPrice[0]);
        //calculate the total price:
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User ", "user id", username + ""));


        order.setUser(user);

        //how to save order to db
        this.orderRepo.save(order);

        cart.getItems().clear();

        this.cartRepo.save(cart);


    }

    @Override
    public List<OrderDto> getOrders() {
        List<Order> all = this.orderRepo.findAll();
        List<OrderDto> data = all.stream().map((order -> this.modelMapper.map(order, OrderDto.class))).collect(Collectors.toList());
        return data;
    }

	@Override
	public void delete(Integer orderId) {
		// TODO Auto-generated method stub
		Order order = this.orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("order", "order id", orderId + ""));
    	this.orderRepo.delete(order);
	}

	@Override
	public OrderDto getById(Integer orderId) {
		// TODO Auto-generated method stub
		Order order = this.orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order", "order id ", orderId + ""));

        return this.modelMapper.map(order, OrderDto.class);
	}
}
