package be_viemp3.viemp3.repository.analytics;

import be_viemp3.viemp3.entity.ListenHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListenHistoryRepository extends JpaRepository<ListenHistory, Long> {
}
