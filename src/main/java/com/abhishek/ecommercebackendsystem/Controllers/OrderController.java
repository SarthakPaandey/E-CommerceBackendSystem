package com.abhishek.ecommercebackendsystem.Controllers;

import com.abhishek.ecommercebackendsystem.Dtos.OrderRequestDto;
import com.abhishek.ecommercebackendsystem.Dtos.OrderResponseDto;
import com.abhishek.ecommercebackendsystem.Dtos.ProductResponseDto;
import com.abhishek.ecommercebackendsystem.Models.Orders;
import com.abhishek.ecommercebackendsystem.Services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private ModelMapper modelMapper;

    private OrderService orderService;
    private ProductController productController;
    public OrderController(OrderService orderService, ProductController productController) {
        this.orderService = orderService;
        this.productController = productController;
    }

    @PostMapping("/")
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Orders savedOrder =  orderService.createOrder(orderRequestDto);
        OrderResponseDto orderResponseDto = modelMapper.map(savedOrder, OrderResponseDto.class);

        List<Long> productIds = savedOrder.getProductIds();
        for (int j=0; j<productIds.size(); j++) {
            ProductResponseDto productResponseDto = productController.getProductById(productIds.get(j));

            orderResponseDto.getProductResponseDtoList().add(productResponseDto);
        }
        return orderResponseDto;
    }
}
