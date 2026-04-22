package be_viemp3.viemp3.service.finance;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.config.VNPayConfig;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final EntityQueryService entityService;
    private final VNPayConfig vnPayConfig;

    public String createPaymentUrl(String orderId, HttpServletRequest httpRequest) {
        Order order = entityService.findOrderById(orderId);
        long amount = (long) (order.getTotalPrice() * 100);
        String vnp_TxnRef = order.getVnpTxnRef();
        String vnp_IpAddr = vnPayConfig.getIpAddress(httpRequest);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnPayConfig.getVnpTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", order.getId());
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getVnpReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));

        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));

        // Sắp xếp và build query string
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                // Build hash data
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if (i < fieldNames.size() - 1) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        // Gọi hàm băm từ config
        String vnp_SecureHash = vnPayConfig.hmacSHA512(hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        return vnPayConfig.getVnpPayUrl() + "?" + queryUrl;
    }

    @Transactional
    public boolean processPaymentCallback(Map<String, String> params) {
        // 1. Lấy mã Hash từ tham số VNPay gửi về
        String vnp_SecureHash = params.get("vnp_SecureHash");

        // 2. Tạo bản sao của params và loại bỏ key SecureHash để tính toán lại mã hash
        Map<String, String> hashParams = new HashMap<>(params);
        hashParams.remove("vnp_SecureHash");
        hashParams.remove("vnp_SecureHashType");

        // 3. Sắp xếp và build chuỗi hashData y hệt lúc tạo URL
        List<String> fieldNames = new ArrayList<>(hashParams.keySet());
        Collections.sort(fieldNames);

        StringJoiner hashData = new StringJoiner("&");
        for (String fieldName : fieldNames) {
            String fieldValue = hashParams.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.add(fieldName + "=" + URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
            }
        }

        // 4. Kiểm tra chữ ký (Verify)
        String builtHash = vnPayConfig.hmacSHA512(hashData.toString());
        if (!builtHash.equalsIgnoreCase(vnp_SecureHash)) {
            return false; // Chữ ký không khớp, nghi ngờ giả mạo
        }

        // 5. Xử lý logic nghiệp vụ
        String responseCode = params.get("vnp_ResponseCode");
        String txnRef = params.get("vnp_TxnRef");

        // Tìm Order dựa trên mã giao dịch bạn đã lưu (vnpTxnRef)
        Order order = orderRepository.findByVnpTxnRef(txnRef)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        if ("00".equals(responseCode)) {
            // Cập nhật trạng thái đơn hàng
            order.setStatus(OrderStatus.COMPLETED);
            order.setOrderDate(LocalDateTime.now());

            // Tính toán ngày hết hạn (Ví dụ: gói 1 tháng dựa theo gói cước aPackage của order)
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
            // Thanh toán thất bại hoặc người dùng hủy
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
            return false;
        }
    }
}
