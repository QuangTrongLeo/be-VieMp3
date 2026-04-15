package be_viemp3.viemp3.dto.response.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListenStatisticsResponse {
    private String period;
    private Long totalListen;
}
