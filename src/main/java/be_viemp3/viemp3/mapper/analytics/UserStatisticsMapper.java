package be_viemp3.viemp3.mapper.analytics;

import be_viemp3.viemp3.dto.response.analytics.UserStatisticsResponse;
import org.springframework.stereotype.Component;

@Component
public class UserStatisticsMapper {

    public UserStatisticsResponse toResponse(String label, long count, long total) {
        return UserStatisticsResponse.builder()
                .label(label)
                .count(count)
                .percentage(total > 0 ? Math.round(((double) count / total * 100) * 10) / 10.0 : 0)
                .build();
    }
}
