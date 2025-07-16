package com.company.ecommerce.service;

import com.company.ecommerce.dto.CheckoutItemDto;
import com.company.ecommerce.dto.cart.CartDto;
import com.company.ecommerce.dto.cart.CartItemDto;
import com.company.ecommerce.dto.product.ProductDto;
import com.company.ecommerce.exception.OrderNotFoundException;
import com.company.ecommerce.model.*;
import com.company.ecommerce.repository.CartRepository;
import com.company.ecommerce.repository.OrderItemsRepository;
import com.company.ecommerce.repository.OrderRepository;
import com.company.ecommerce.repository.PurchaseItemSnapshotRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private PurchaseItemSnapshotRepository purchasedItemSnapshotRepository;

    @Autowired
    private CartRepository cartRepository;

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount( ((long) checkoutItemDto.getPrice()) * 100)
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build())
                .build();
    }

    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        String successURL = baseURL + "payment/success";
        String failedURL = baseURL + "payment/failed";

        Stripe.apiKey = apiKey;
        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();

        for (CheckoutItemDto checkoutItemDto : checkoutItemDtoList) {
            sessionItemsList.add(createSessionLineItem(checkoutItemDto));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failedURL)
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(params);
    }

    public void placeOrder(User user, String sessionId) {
        PurchasedItemSnapshot purchasedItemSnapshot = null;
        CartDto cartDto = cartService.listCartItems(user);
        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        Order newOrder = new Order(user,sessionId,new Date(),cartDto.getTotalCost());
        orderRepository.save(newOrder);

        for(CartItemDto cartItemDto: cartItemDtoList){
            purchasedItemSnapshot = new PurchasedItemSnapshot(cartItemDto.getProduct());
            if(!purchasedItemSnapshotRepository.existsByName(purchasedItemSnapshot.getName())){
                purchasedItemSnapshot = purchasedItemSnapshotRepository.save(purchasedItemSnapshot);
            }else{
                purchasedItemSnapshot = purchasedItemSnapshotRepository.findByName(purchasedItemSnapshot.getName());
            }

            OrderItem orderItem = new OrderItem(new Date(), cartItemDto.getProduct().getPrice(), purchasedItemSnapshot, cartItemDto.getQuantity(), newOrder);
            orderItemsRepository.save(orderItem);

            ProductDto productDto = new ProductDto(cartItemDto.getProduct());
            productDto.setQuantity(cartItemDto.getProduct().getQuantity() - cartItemDto.getQuantity());
            productService.updateProduct(cartItemDto.getProduct().getId(), productDto,cartItemDto.getProduct().getCategory());

            cartRepository.deleteById(cartItemDto.getId());
        }
    }

    public List<Order> listOrders(User user) {
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }


    public Order getOrder(Integer orderId, User user) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            throw  new OrderNotFoundException("order id is not valid");
        }
        if(order.getUser() != user) {
            throw  new OrderNotFoundException("order does not belong to user");
        }
        return  order;
    }
}
