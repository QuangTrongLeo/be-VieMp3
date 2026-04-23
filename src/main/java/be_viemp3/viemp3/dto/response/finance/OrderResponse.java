package be_viemp3.viemp3.dto.response.finance;

import be_viemp3.viemp3.dto.response.auth.UserResponse;
import be_viemp3.viemp3.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private String id;
    private PackageResponse pkg;
    private Double totalPrice;
    private LocalDateTime orderDate;
    private LocalDateTime expiryDate;
    private OrderStatus status;
    private String vnpTxnRef;
    private UserResponse user;
}
