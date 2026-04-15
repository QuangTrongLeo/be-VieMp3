package be_viemp3.viemp3.controller.analytics;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.response.analytics.GenreStatisticsResponse;
import be_viemp3.viemp3.dto.response.analytics.ListenStatisticsResponse;
import be_viemp3.viemp3.service.analytic.GenreStatisticsService;
import be_viemp3.viemp3.service.analytic.ListenStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("${api.vie-mp3-url}/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final ListenStatisticsService listenStatisticsService;
    private final GenreStatisticsService genreStatisticsService;

    // ===== LISTEN =====
    @GetMapping("/listen/month")
    public ApiResponse<List<ListenStatisticsResponse>> getListenByMonth() {
        return ApiResponse.<List<ListenStatisticsResponse>>builder()
                .success(true)
                .message("Lấy thống kê lượt nghe theo tháng thành công")
                .data(listenStatisticsService.getListenByMonth())
                .build();
    }

    @GetMapping("/listen/week")
    public ApiResponse<List<ListenStatisticsResponse>> getListenByWeek() {
        return ApiResponse.<List<ListenStatisticsResponse>>builder()
                .success(true)
                .message("Lấy thống kê lượt nghe theo tuần thành công")
                .data(listenStatisticsService.getListenByWeek())
                .build();
    }

    // ===== GENRE =====
    @GetMapping("/genres")
    public ApiResponse<List<GenreStatisticsResponse>> getGenreStatistics() {
        return ApiResponse.<List<GenreStatisticsResponse>>builder()
                .success(true)
                .message("Lấy thống kê tỷ trọng thể loại thành công")
                .data(genreStatisticsService.getGenreStatistics())
                .build();
    }
}
