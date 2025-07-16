package com.company.ecommerce;

import com.company.ecommerce.dto.Order.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class EcommerceApplicationTests {

	@Test
	void contextLoads() {
		OrderDto orderDto = new OrderDto(4,23.56, 5);
		int orderIdActual = 4;
		assertEquals(orderIdActual,orderDto.getOrderId());
	}

	@Test
	void contextLoadsFail() {
		OrderDto orderDto = new OrderDto(5,23.56, 5);
		int orderIdActual = 4;
		assertNotEquals(orderIdActual,orderDto.getOrderId());
	}
}
