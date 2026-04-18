package be_viemp3.viemp3.service.finance;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.dto.request.finance.OrderRequest;
import be_viemp3.viemp3.dto.response.finance.OrderResponse;
import be_viemp3.viemp3.entity.Order;
import be_viemp3.viemp3.entity.Packages;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.entity.Voucher;
import be_viemp3.viemp3.enums.OrderStatus;
import be_viemp3.viemp3.mapper.finance.OrderMapper;
import be_viemp3.viemp3.repository.finance.OrderRepository;
import be_viemp3.viemp3.service.auth.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EntityQueryService entityService;
    private final SecurityService securityService;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        User user = securityService.getCurrentUser();
        Packages pkg = entityService.findPackageById(orderRequest.getPackageId());

        Voucher voucher = (orderRequest.getVoucherId() != null)
                ? entityService.finVoucherById(orderRequest.getVoucherId()) : null;

        Double totalPrice = orderRequest.getTotalPrice();
        String txnRef = String.valueOf(System.currentTimeMillis());

        Order order = new Order();
        order.setUser(user);
        order.setAPackage(pkg);
        order.setVoucher(voucher);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setVnpTxnRef(txnRef);

        orderRepository.save(order);

        return OrderMapper.toResponse(order);
    }
}