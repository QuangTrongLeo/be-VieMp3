package be_viemp3.viemp3.service.finance;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.entity.Order;
import be_viemp3.viemp3.entity.Role;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.enums.OrderStatus;
import be_viemp3.viemp3.enums.RoleEnum;
import be_viemp3.viemp3.repository.auth.UserRepository;
import be_viemp3.viemp3.repository.finance.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final EntityQueryService entityService;
    private final VnPayService vnPayService;

    public String createVnPayUrl(String txnRef, HttpServletRequest request) {
        Order order = entityService.findOrderByVnpTxnRef(txnRef);
        return vnPayService.createPaymentUrl(
                order.getTotalPrice().longValue(),
                "Thanh toan goi " + order.getAPackage().getPkg(),
                order.getVnpTxnRef(),
                request
        );
    }

    @Transactional
    public boolean processPaymentCallback(Map<String, String> params) {
        String responseCode = params.get("vnp_ResponseCode");
        String txnRef = params.get("vnp_TxnRef");

        Order order = entityService.findOrderByVnpTxnRef(txnRef);

        if ("00".equals(responseCode)) {
            // Cập nhật trạng thái đơn hàng
            order.setStatus(OrderStatus.COMPLETED);
            order.setExpiryDate(LocalDateTime.now().plusMonths(1));

            // Nâng cấp Role cho User
            User user = order.getUser();
            Role premiumRole = entityService.findRoleByName(RoleEnum.PREMIUM);

            if (!user.getRoles().contains(premiumRole)) {
                user.getRoles().add(premiumRole);
                userRepository.save(user);
            }

            orderRepository.save(order);
            return true;
        } else {
            // Thanh toán thất bại
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
            return false;
        }
    }
}