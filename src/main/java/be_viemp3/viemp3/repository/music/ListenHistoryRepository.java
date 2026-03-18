package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.ListenHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListenHistoryRepository extends JpaRepository<ListenHistory, String> {
    List<ListenHistory> findByUserIdOrderByListenedAtDesc(String userId);
    Optional<ListenHistory> findByUserIdAndSongId(String userId, String songId);
    List<ListenHistory> findByUserIdOrderByListenedAtAsc(String userId);

    long countByUserId(String userId);

    void deleteById(String id);
}
