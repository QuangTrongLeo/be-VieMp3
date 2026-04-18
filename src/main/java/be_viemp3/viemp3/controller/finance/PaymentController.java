package be_viemp3.viemp3.controller.finance;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.service.finance.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.vie-mp3-url}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
    @GetMapping("/vn-pay-redirect/{txnRef}")
    public ResponseEntity<ApiResponse<Map<String, String>>> redirectToVnPay(
            @PathVariable String txnRef,
            HttpServletRequest request) {

        // Gọi service để lấy URL đã được build hoàn chỉnh
        String paymentUrl = paymentService.createVnPayUrl(txnRef, request);

        Map<String, String> data = new HashMap<>();
        data.put("paymentUrl", paymentUrl);

        return ResponseEntity.ok(
                ApiResponse.<Map<String, String>>builder()
                        .success(true)
                        .message("Yêu cầu chuyển hướng thanh toán đã được xử lý")
                        .data(data)
                        .build()
        );
    }

    @GetMapping("/vn-pay-callback")
    public ResponseEntity<ApiResponse<Map<String, String>>> vnPayCallback(HttpServletRequest request) {
        Map<String, String> params = request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));

        boolean isSuccess = paymentService.processPaymentCallback(params);

        Map<String, String> resultData = new HashMap<>();
        resultData.put("txnRef", params.get("vnp_TxnRef"));

        return ResponseEntity.ok(
                ApiResponse.<Map<String, String>>builder()
                        .success(isSuccess)
                        .message(isSuccess ? "Thanh toán thành công. Tài khoản đã nâng cấp PREMIUM."
                                : "Thanh toán không thành công. Mã lỗi: " + params.get("vnp_ResponseCode"))
                        .data(resultData)
                        .build()
        );
    }
}