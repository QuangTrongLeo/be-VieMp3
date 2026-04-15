package be_viemp3.viemp3.service.analytic;

import be_viemp3.viemp3.dto.response.analytics.ListenStatisticsResponse;
import be_viemp3.viemp3.repository.music.ListenHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListenStatisticsService {
    private final ListenHistoryRepository listenHistoryRepository;

    public List<ListenStatisticsResponse> getListenByMonth() {
        return listenHistoryRepository.getListenStatsByMonthNative().stream()
                .map(obj -> new ListenStatisticsResponse(
                        (String) obj[0],
                        ((Number) obj[1]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<ListenStatisticsResponse> getListenByWeek() {
        return listenHistoryRepository.getListenStatsByWeekNative().stream()
                .map(obj -> new ListenStatisticsResponse(
                        (String) obj[0],
                        ((Number) obj[1]).longValue()
                ))
                .collect(Collectors.toList());
    }
}
