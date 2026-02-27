package be_viemp3.viemp3.repository.analytics;

import be_viemp3.viemp3.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, UUID> {
}
