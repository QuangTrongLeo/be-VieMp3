package be_viemp3.viemp3.repository.subscription;

import be_viemp3.viemp3.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByUserIdOrderByNotificationAtDesc(String userId);
}
