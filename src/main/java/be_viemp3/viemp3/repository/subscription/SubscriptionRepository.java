package be_viemp3.viemp3.repository.subscription;

import be_viemp3.viemp3.entity.Subscription;
import be_viemp3.viemp3.enums.SubscriptionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    boolean existsByName(SubscriptionEnum subscription);
}
