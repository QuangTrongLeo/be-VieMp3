package be_viemp3.viemp3.service.finance;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.config.VNPayConfig;
import be_viemp3.viemp3.dto.request.finance.OrderRequest;
import be_viemp3.viemp3.dto.response.finance.OrderResponse;
import be_viemp3.viemp3.entity.*;
import be_viemp3.viemp3.enums.OrderStatus;
import be_viemp3.viemp3.enums.RoleEnum;
import be_viemp3.viemp3.mapper.finance.OrderMapper;
import be_viemp3.viemp3.repository.auth.UserRepository;
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
    private final UserRepository userRepository;
    private final EntityQueryService entityService;
    private final SecurityService securityService;
    private final VNPayConfig vnPayConfig;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        User user = securityService.getCurrentUser();
        Packages pkg = entityService.findPackageById(orderRequest.getPackageId());

        Voucher voucher = (orderRequest.getVoucherId() != null)
                ? entityService.finVoucherById(orderRequest.getVoucherId()) : null;

        Double totalPrice = orderRequest.getTotalPrice();
        String txnRef = vnPayConfig.getRandomNumber(8);

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

    @Transactional
    public void completeOrder(String txnRef) {
        Order order = entityService.findOrderByVnpTxnRef(txnRef);

        // 1. Cập nhật trạng thái đơn hàng
        order.setStatus(OrderStatus.COMPLETED);
        order.setOrderDate(LocalDateTime.now());

//        int durations = order.getAPackage().getDuration();
        order.setExpiryDate(LocalDateTime.now().plusMonths(1));

        // 2. Nâng cấp Role cho User
        User user = order.getUser();
        Role premiumRole = entityService.findRoleByName(RoleEnum.PREMIUM);
        if (!user.getRoles().contains(premiumRole)) {
            user.getRoles().add(premiumRole);
            userRepository.save(user);
        }

        orderRepository.save(order);
    }

    @Transactional
    public void failOrder(String txnRef) {
        Order order = entityService.findOrderByVnpTxnRef(txnRef);
        order.setStatus(OrderStatus.FAILED);
        orderRepository.save(order);
    }
}