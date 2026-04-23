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
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VoucherService voucherService;
    private final EntityQueryService entityService;
    private final SecurityService securityService;
    private final TaskScheduler taskScheduler;
    private final VNPayConfig vnPayConfig;

    // ===== CREATE ORDER =====
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

        Order savedOrder = orderRepository.save(order);

        // Lên lịch xóa sau 10 phút nếu không cập nhật trạng thái đơn hàng
        scheduleOrderCleanup(savedOrder.getId());

        return OrderMapper.toResponse(savedOrder);
    }

    // ===== GET ALL ORDER =====
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAllByOrderByOrderDateDesc();
        return OrderMapper.toResponseList(orders);
    }

    // ===== GET ALL ORDER BY USER
    public List<OrderResponse> getAllOrdersByUser() {
        User user = securityService.getCurrentUser();
        List<Order> orders = orderRepository.findAllByUserOrderByOrderDateDesc(user);
        return OrderMapper.toResponseList(orders);
    }

    // ===== GET ORDER BY ID =====
    public OrderResponse getOrderById(String orderId) {
        Order order = entityService.findOrderById(orderId);
        return OrderMapper.toResponse(order);
    }

    @Transactional
    public void completeOrder(String txnRef) {
        Order order = entityService.findOrderByVnpTxnRef(txnRef);

        // 1. Cập nhật trạng thái đơn hàng
        order.setStatus(OrderStatus.COMPLETED);
        order.setOrderDate(LocalDateTime.now());

        int durationMonths = order.getAPackage().getDuration().getMonths();
        order.setExpiryDate(LocalDateTime.now().plusMonths(durationMonths));

        // 2. Nâng cấp Role cho User
        User user = order.getUser();
        Role premiumRole = entityService.findRoleByName(RoleEnum.PREMIUM);
        if (!user.getRoles().contains(premiumRole)) {
            user.getRoles().add(premiumRole);
            userRepository.save(user);
        }

        if (order.getVoucher() != null) {
            voucherService.useVoucher(order.getVoucher().getId());
        }

        orderRepository.save(order);
    }

    @Transactional
    public void failOrder(String txnRef) {
        Order order = entityService.findOrderByVnpTxnRef(txnRef);
        order.setStatus(OrderStatus.FAILED);
        orderRepository.save(order);
    }

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void cleanupExpiredPremiumRoles() {
        LocalDateTime now = LocalDateTime.now();

        // 1. Tìm các đơn hàng đã hết hạn nhưng chưa được xử lý "hết hạn"
        List<Order> expiredOrders = orderRepository.findByStatusAndExpiryDateBefore(OrderStatus.COMPLETED, now);

        for (Order order : expiredOrders) {
            User user = order.getUser();

            // 2. Kiểm tra xem user còn đơn hàng PREMIUM nào khác còn hạn không
            boolean hasActiveOrder = orderRepository.existsByUserAndStatusAndExpiryDateAfter(
                    user, OrderStatus.COMPLETED, now);

            if (!hasActiveOrder) {
                // 3. Xóa role PREMIUM
                user.getRoles().removeIf(role -> role.getName().equals(RoleEnum.PREMIUM));
                userRepository.save(user);
            }

            // 4. Đánh dấu đơn hàng này đã xử lý hết hạn (tùy chọn)
            order.setStatus(OrderStatus.EXPIRED);
            orderRepository.save(order);
        }
    }

    private void scheduleOrderCleanup(String orderId) {
        taskScheduler.schedule(
                () -> {
                    // Chỉ xóa nếu đơn hàng vẫn đang ở trạng thái PENDING
                    orderRepository.deleteByIdAndStatus(orderId, OrderStatus.PENDING);
                    System.out.println("Đã thực hiện kiểm tra và dọn dẹp đơn hàng: " + orderId);
                },
                Instant.now().plus(10, ChronoUnit.MINUTES)
        );
    }
}