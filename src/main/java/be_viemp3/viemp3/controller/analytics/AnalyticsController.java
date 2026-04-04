package be_viemp3.viemp3.controller.analytics;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.response.analytics.ListenStatsResponse;
import be_viemp3.viemp3.service.analytic.ListenStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final ListenStatsService listenStatsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listen/month")
    public ApiResponse<List<ListenStatsResponse>> getListenByMonth() {
        return ApiResponse.<List<ListenStatsResponse>>builder()
                .success(true)
                .message("Lấy thống kê lượt nghe theo tháng thành công")
                .data(listenStatsService.getListenByMonth())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listen/week")
    public ApiResponse<List<ListenStatsResponse>> getListenByWeek() {
        return ApiResponse.<List<ListenStatsResponse>>builder()
                .success(true)
                .message("Lấy thống kê lượt nghe theo tuần thành công")
                .data(listenStatsService.getListenByWeek())
                .build();
    }
}
