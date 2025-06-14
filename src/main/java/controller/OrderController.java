package controller;

import model.dto.OrderResponseDto;
import model.service.OrderServiceImp;

import java.util.List;

public class OrderController {
    private OrderServiceImp orderService;

    // No-arg constructor initializes orderService to prevent null pointer
    public OrderController() {
        this.orderService = new OrderServiceImp();
    }

    // Constructor injection if you want to pass your own instance (e.g. for testing)
    public OrderController(OrderServiceImp orderService) {
        this.orderService = orderService;
    }

    public OrderResponseDto processOrder(String uuid, List<String> productUuid) {
//        System.out.println(uuid);
//        productUuid.forEach(System.out::println);
        System.out.println("processOrder...");
        return orderService.makeOrder(uuid, productUuid);
    }
}
