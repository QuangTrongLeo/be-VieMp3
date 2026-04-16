package be_viemp3.viemp3.mapper.finance;

import be_viemp3.viemp3.dto.response.finance.NotificationResponse;
import be_viemp3.viemp3.entity.Notification;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {

    public static NotificationResponse toResponse(Notification notification) {
        if (notification == null) {
            return null;
        }

        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .cover(notification.getCover())
                .notificationAt(notification.getNotificationAt())
                .isRead(notification.isRead())
                .build();
    }

    public static List<NotificationResponse> toResponseList(List<Notification> notifications) {
        if (notifications == null) {
            return List.of();
        }

        return notifications.stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }
}