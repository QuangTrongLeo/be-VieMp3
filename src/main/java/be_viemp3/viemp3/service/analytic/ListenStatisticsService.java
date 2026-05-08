package be_viemp3.viemp3.service.analytic;

import be_viemp3.viemp3.dto.response.analytics.ListenStatisticsResponse;
import be_viemp3.viemp3.mapper.analytics.ListenStatisticsMapper;
import be_viemp3.viemp3.repository.music.ListenHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListenStatisticsService {
    private final ListenHistoryRepository listenHistoryRepository;
    private final ListenStatisticsMapper listenStatisticsMapper;

    // Thống kê theo ngày
    public List<ListenStatisticsResponse> getListenByDay() {
        List<Object[]> results = listenHistoryRepository.getListenStatsByDayNative();
        return listenStatisticsMapper.toResponseList(results);
    }

    // Thống kê theo tuần
    public List<ListenStatisticsResponse> getListenByWeek() {
        List<Object[]> results = listenHistoryRepository.getListenStatsByWeekNative();
        return listenStatisticsMapper.toResponseList(results);
    }

    // Thống kê theo tháng
    public List<ListenStatisticsResponse> getListenByMonth() {
        List<Object[]> results = listenHistoryRepository.getListenStatsByMonthNative();
        return listenStatisticsMapper.toResponseList(results);
    }
}
