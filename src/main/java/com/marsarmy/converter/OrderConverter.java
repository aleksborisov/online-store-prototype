package com.marsarmy.converter;

import com.marsarmy.dto.OrderDto;
import com.marsarmy.dto.ProductsInOrderDto;
import com.marsarmy.model.Order;
import com.marsarmy.model.ProductsInOrder;
import com.marsarmy.model.enumeration.DeliveryMethod;
import com.marsarmy.model.enumeration.OrderStatus;
import com.marsarmy.model.enumeration.PaymentMethod;
import com.marsarmy.model.enumeration.PaymentStatus;
import com.marsarmy.service.interf.CustomerService;
import com.marsarmy.service.interf.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter for Order entity and OrderDto
 */
@Component
public class OrderConverter {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final ProductsInOrderConverter productsInOrderConverter;

    @Autowired
    public OrderConverter(CustomerService customerService, OrderService orderService,
                          ProductsInOrderConverter productsInOrderConverter) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.productsInOrderConverter = productsInOrderConverter;
    }

    /**
     * Convert Order entity to OrderDto
     *
     * @param order Order entity to convert
     * @return {@link OrderDto}
     */
    public OrderDto convertToDto(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setCustomerDto(order.getCustomer().getEmail());
        orderDto.setPaymentMethod(order.getPaymentMethod().name());
        orderDto.setDeliveryMethod(order.getDeliveryMethod().name());
        orderDto.setPaymentStatus(order.getPaymentStatus().name());
        orderDto.setOrderStatus(order.getOrderStatus().name());
        orderDto.setAddress(order.getAddress());
        orderDto.setTotal(order.getTotal());
        orderDto.setDateOfSale(order.getDateOfSale());

        List<ProductsInOrderDto> productsInOrdersDto = new ArrayList<>();
        for (ProductsInOrder productsInOrder : order.getProductsInOrders()) {
            productsInOrdersDto.add(productsInOrderConverter.convertToDto(productsInOrder));
        }
        orderDto.setProductsInOrdersDto(productsInOrdersDto);

        return orderDto;
    }

    /**
     * Convert OrderDto to Order entity
     *
     * @param orderDto OrderDto to convert
     * @return {@link Order}
     */
    public Order convertToEntity(OrderDto orderDto) {
        Order order = new Order();

        if (orderDto.getId() != null) {
            order.setId(orderDto.getId());
        }

        order.setCustomer(customerService.getOne(orderDto.getCustomerDto()));
        order.setPaymentMethod(PaymentMethod.valueOf(orderDto.getPaymentMethod()));
        order.setDeliveryMethod(DeliveryMethod.valueOf(orderDto.getDeliveryMethod()));
        order.setPaymentStatus(PaymentStatus.valueOf(orderDto.getPaymentStatus()));
        order.setOrderStatus(OrderStatus.valueOf(orderDto.getOrderStatus()));
        order.setAddress(orderDto.getAddress());
        order.setTotal(orderDto.getTotal());
        order.setDateOfSale(orderDto.getDateOfSale());

        if (orderDto.getProductsInOrdersDto() == null) {
            if (orderDto.getId() != null) {
                Order orderInDb = orderService.getOne(orderDto.getId());
                if (orderInDb == null) {
                    order.setProductsInOrders(new ArrayList<>());
                } else {
                    order.setProductsInOrders(orderInDb.getProductsInOrders());
                }
            }
        } else {
            order.setProductsInOrders(productsInOrderConverter.convertToListOfEntity(orderDto.getProductsInOrdersDto()));
        }

        return order;
    }

    /**
     * Convert List of Order entities to List of OrderDto
     *
     * @param orders List of Order entities to convert
     * @return {@link List} of {@link OrderDto}
     */
    public List<OrderDto> convertToListOfDto(List<Order> orders) {
        List<OrderDto> ordersDto = new ArrayList<>();
        for (Order order : orders) {
            ordersDto.add(convertToDto(order));
        }
        return ordersDto;
    }

    /**
     * Convert List of OrderDto to List of Order entities
     *
     * @param ordersDto List of OrderDto to convert
     * @return {@link List} of {@link Order}
     */
    public List<Order> convertToListOfEntity(List<OrderDto> ordersDto) {
        List<Order> orders = new ArrayList<>();
        for (OrderDto orderDto : ordersDto) {
            orders.add(convertToEntity(orderDto));
        }
        return orders;
    }
}
