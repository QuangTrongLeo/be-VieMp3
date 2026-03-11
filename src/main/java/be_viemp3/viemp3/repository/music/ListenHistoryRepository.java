package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.ListenHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListenHistoryRepository extends JpaRepository<ListenHistory, String> {
    List<ListenHistory> findByUserIdOrderByListenedAtDesc(String userId);
}
