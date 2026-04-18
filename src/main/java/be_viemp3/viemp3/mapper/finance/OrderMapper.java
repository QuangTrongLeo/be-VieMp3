package be_viemp3.viemp3.mapper.finance;

import be_viemp3.viemp3.dto.response.finance.OrderResponse;
import be_viemp3.viemp3.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .pkg(PackageMapper.toResponse(order.getAPackage()))
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .expiryDate(order.getExpiryDate())
                .status(order.getStatus())
                .vnpTxnRef(order.getVnpTxnRef())
                .build();
    }

    public static List<OrderResponse> toResponseList(List<Order> orders) {
        if (orders == null) {
            return List.of();
        }

        return orders.stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }
}