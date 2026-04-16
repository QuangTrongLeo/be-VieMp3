package be_viemp3.viemp3.controller.finance;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.response.finance.NotificationResponse;
import be_viemp3.viemp3.service.finance.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getMyNotifications() {
        List<NotificationResponse> responses = notificationService.getMyNotifications();
        return ResponseEntity.ok(
                ApiResponse.<List<NotificationResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách thông báo thành công")
                        .data(responses)
                        .build()
        );
    }
}
