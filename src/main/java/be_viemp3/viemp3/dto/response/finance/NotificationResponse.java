package be_viemp3.viemp3.dto.response.finance;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class NotificationResponse {

    private String id;

    private String title;

    private String cover;

    private OffsetDateTime notificationAt;

    private boolean isRead;
}
