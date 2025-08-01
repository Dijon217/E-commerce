package com.company.ecommerce.controller;

import com.company.ecommerce.config.ApiResponse;
import com.company.ecommerce.config.StripeResponse;
import com.company.ecommerce.dto.CheckoutItemDto;
import com.company.ecommerce.dto.Order.OrderDto;
import com.company.ecommerce.exception.AuthenticationFailException;
import com.company.ecommerce.exception.OrderNotFoundException;
import com.company.ecommerce.model.Order;
import com.company.ecommerce.model.User;
import com.company.ecommerce.service.AuthenticationService;
import com.company.ecommerce.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        List<Order> orderList = orderService.listOrders(user);

        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Order o: orderList){
            OrderDto orderDto = new OrderDto(o);
            System.out.println(orderDto.toString());
            orderDtoList.add(orderDto);
        }
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token)
            throws AuthenticationFailException, OrderNotFoundException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Order order = orderService.getOrder(id, user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
