package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.dto.response.analytics.ListenStatsResponse;
import be_viemp3.viemp3.entity.ListenHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ListenHistoryRepository extends JpaRepository<ListenHistory, String> {
    Optional<ListenHistory> findByUserIdAndSongId(String userId, String songId);
    List<ListenHistory> findByUserIdOrderByListenedAtDesc(String userId);
    List<ListenHistory> findByUserIdOrderByListenedAtAsc(String userId);
    List<ListenHistory> findByUserId(String userId);
    long countByUserId(String userId);
    void deleteById(String id);

    @Query(value = """
        SELECT 
            DATE_FORMAT(listened_at, '%x-%v') as period, 
            COUNT(id) as totalListen 
        FROM listen_history 
        GROUP BY period 
        ORDER BY period ASC
    """, nativeQuery = true)
    List<Object[]> getListenStatsByWeekNative();

    // Thống kê theo tháng
    @Query(value = """
        SELECT 
            DATE_FORMAT(listened_at, '%Y-%m') as period, 
            COUNT(id) as totalListen 
        FROM listen_history 
        GROUP BY period 
        ORDER BY period ASC
    """, nativeQuery = true)
    List<Object[]> getListenStatsByMonthNative();
}
